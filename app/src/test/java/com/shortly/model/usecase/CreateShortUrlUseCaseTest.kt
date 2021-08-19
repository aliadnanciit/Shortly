package com.shortly.model.usecase

import com.shortly.model.common.rule.UrlValidationRule
import com.shortly.model.exception.InvalidURLException
import com.shortly.model.datamodel.ShortURL
import com.shortly.model.repository.ShortenRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class CreateShortUrlUseCaseTest {

    @MockK
    private lateinit var urlValidationRule: UrlValidationRule
    @MockK
    private lateinit var shortenRepository: ShortenRepository

    private lateinit var createShortUrlUseCase: CreateShortUrlUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        createShortUrlUseCase = CreateShortUrlUseCase(urlValidationRule, shortenRepository)
    }

    @Test(expected = InvalidURLException::class)
    fun `verify error for empty url`() = runBlockingTest {
        val url = ""
        createShortUrlUseCase.create(url)
    }

    @Test(expected = InvalidURLException::class)
    fun `verify error if url is not valid`() = runBlockingTest {
        val url = "abc.com"
        every { urlValidationRule.isValid(url) } returns false
        createShortUrlUseCase.create(url)
    }

    @Test
    fun `create shorten url if url is valid`() = runBlockingTest {
        val url = "https://abc.com"
        every { urlValidationRule.isValid(url) } returns true
        val shortUrl = mockk<ShortURL>()
        coEvery { shortenRepository.createShortenUrl(url) } returns shortUrl

        createShortUrlUseCase.create(url)

        coVerify { shortenRepository.createShortenUrl(url) }
    }

    @Test
    fun `create shorten url if url is valid and has spaces`() = runBlockingTest {
        val url = "https://abc.com?a=b &c=d"
        val newUrl = "https://abc.com?a=b%20&c=d"
        every { urlValidationRule.isValid(newUrl) } returns true
        val shortUrl = mockk<ShortURL>()
        coEvery { shortenRepository.createShortenUrl(newUrl) } returns shortUrl

        createShortUrlUseCase.create(url)

        coVerify { shortenRepository.createShortenUrl(newUrl) }
    }
}