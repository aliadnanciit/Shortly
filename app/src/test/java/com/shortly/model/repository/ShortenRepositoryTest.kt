package com.shortly.model.repository

import com.shortly.model.common.ErrorHandler
import com.shortly.model.database.HistoryDao
import com.shortly.model.database.HistoryEntity
import com.shortly.model.exception.InvalidURLException
import com.shortly.model.datamodel.ErrorResponse
import com.shortly.model.datamodel.ShortURL
import com.shortly.model.datamodel.ShortenApiResponse
import com.shortly.model.service.ShortlyApi
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ShortenRepositoryTest {

    @MockK
    private lateinit var shortlyApi: ShortlyApi
    @MockK
    private lateinit var historyDao: HistoryDao
    @MockK
    private lateinit var errorHandler: ErrorHandler

    private lateinit var shortenRepository: ShortenRepository

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        shortenRepository = ShortenRepositoryImpl(
            shortlyApi, historyDao, errorHandler, testCoroutineDispatcher
        )
    }

    @Test(expected = InvalidURLException::class)
    fun `verify server error for invalid url`() = runBlockingTest {
    val url = "abc"
        coEvery { shortlyApi.createShortenUrl(url) } returns Response.error(400, "server error".toResponseBody())
        val errorResponse = mockk<ErrorResponse>()
        every { errorHandler.parseError(any()) } returns errorResponse
        every { errorResponse.message } returns "server error"

        shortenRepository.createShortenUrl(url)
    }

    @Test
    fun `verify shorten url created successfully and save into database`() = runBlockingTest {
        val url = "abc"
        coEvery { shortlyApi.createShortenUrl(url) } returns Response.success(getShortenApiResponse())
        coEvery { historyDao.insert(getHistoryEntity(getShortUrl())) } returns Unit

        val result = shortenRepository.createShortenUrl(url)

        Assert.assertEquals(getShortUrl(), result)
        coVerify { historyDao.insert(any()) }
    }

    private fun getShortenApiResponse() : ShortenApiResponse {
        return ShortenApiResponse(
            ok = true,
            shortURL = getShortUrl()
        )
    }

    private fun getShortUrl(): ShortURL {
        return ShortURL()
    }

    private fun getHistoryEntity(shortURL: ShortURL): HistoryEntity {
        return HistoryEntity(
            code = shortURL.code,
            shortLink = shortURL.shortLink,
            shortLink2 = shortURL.shortLink2,
            fullShortLink = shortURL.fullShareLink,
            fullShortLink2 = shortURL.fullShortLink2,
            shareLink = shortURL.shareLink,
            fullShareLink = shortURL.fullShareLink
        )
    }
}