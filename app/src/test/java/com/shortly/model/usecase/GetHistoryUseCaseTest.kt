package com.shortly.model.usecase

import com.shortly.model.datamodel.HistoryModel
import com.shortly.model.repository.HistoryRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class GetHistoryUseCaseTest {

    @MockK
    private lateinit var response: Flow<List<HistoryModel>>

    @MockK
    lateinit var historyRepository: HistoryRepository

    private lateinit var getHistoryUseCase: GetHistoryUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getHistoryUseCase = GetHistoryUseCase(historyRepository)
    }

    @Test
    fun `verify getHistory is called`() = runBlockingTest {
        coEvery { historyRepository.getHistory() } returns response

        getHistoryUseCase.getHistory()

        coVerify { historyRepository.getHistory() }
    }

}