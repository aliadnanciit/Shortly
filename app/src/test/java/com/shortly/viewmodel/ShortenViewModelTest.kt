package com.shortly.viewmodel

import com.shortly.model.exception.InvalidURLException
import com.shortly.model.datamodel.ShortURL
import com.shortly.model.datamodel.state.ShortenViewState
import com.shortly.model.usecase.CreateShortUrlUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ShortenViewModelTest {

    @MockK
    private lateinit var createShortUrlUseCase: CreateShortUrlUseCase

    private lateinit var shortenViewModel: ShortenViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        shortenViewModel = ShortenViewModel(createShortUrlUseCase)
    }

    @Test
    fun `verify error state for invalid url`() = runBlockingTest {
        val url = "abc"
        val error = InvalidURLException("")

        coEvery { createShortUrlUseCase.create(url) } throws error

        shortenViewModel.createShortUrl(url)

        Assert.assertEquals(
            ShortenViewState.Error(error),
            shortenViewModel.shortenViewStateFlow.value
        )
    }

    @Test
    fun `verify success state for valid url`() = runBlockingTest {
        val url = "abc"
        val shortUrl = mockk<ShortURL>()

        coEvery { createShortUrlUseCase.create(url) } returns  shortUrl

        shortenViewModel.createShortUrl(url)

        Assert.assertEquals(
            ShortenViewState.SUCCESS,
            shortenViewModel.shortenViewStateFlow.value
        )
    }

    @Test
    fun `verify reset state`() = runBlockingTest {
        shortenViewModel.resetState()

        Assert.assertEquals(
            ShortenViewState.DEFAULT,
            shortenViewModel.shortenViewStateFlow.value
        )
    }
}