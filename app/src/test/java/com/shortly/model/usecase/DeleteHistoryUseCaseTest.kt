package com.shortly.model.usecase

import com.shortly.model.repository.HistoryRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class DeleteHistoryUseCaseTest {

    @MockK
    private lateinit var historyRepository: HistoryRepository

    private lateinit var deleteHistoryUseCase: DeleteHistoryUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        deleteHistoryUseCase = DeleteHistoryUseCase(historyRepository)
    }

    @Test
    fun `verify delete history`() = runBlockingTest {
        coEvery { historyRepository.delete(123) } returns Unit

        deleteHistoryUseCase.delete(123)

        coVerify { historyRepository.delete(123) }
    }

}