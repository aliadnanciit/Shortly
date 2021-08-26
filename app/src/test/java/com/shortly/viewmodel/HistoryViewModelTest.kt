package com.shortly.viewmodel

import androidx.paging.PagingData
import com.shortly.model.datamodel.HistoryModel
import com.shortly.model.datamodel.state.HistoryPagingViewState
import com.shortly.model.datamodel.state.HistoryViewState
import com.shortly.model.usecase.DeleteHistoryUseCase
import com.shortly.model.usecase.GetHistoryUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HistoryViewModelTest {

    @MockK
    private lateinit var pagingData: PagingData<HistoryModel>

    @MockK
    private lateinit var getHistoryUseCase: GetHistoryUseCase
    @MockK(relaxed = true)
    private lateinit var deleteHistoryUseCase: DeleteHistoryUseCase

    private lateinit var historyViewModel: HistoryViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        historyViewModel = HistoryViewModel(getHistoryUseCase, deleteHistoryUseCase)
    }

    @Test
    fun `verify history when history exists`() = runBlockingTest {
        coEvery { getHistoryUseCase.getHistory() } returns flowOf(listOf(getHistoryModel()))

        historyViewModel.getHistory()

        Assert.assertEquals(
            HistoryViewState.Success(listOf(getHistoryModel())), historyViewModel.historyStateFlow.value)
    }

    @Test
    fun `verify history by paging when history exists`() = runBlockingTest {
        coEvery { getHistoryUseCase.getPagingHistory() } returns flowOf(pagingData)

        historyViewModel.getPagingHistory()

        Assert.assertEquals(
            HistoryPagingViewState.Success(pagingData), historyViewModel.historyPagingStateFlow.value)
    }

    @Test
    fun `delete history`() = runBlockingTest {
        historyViewModel.deleteHistory(123)

        coVerify { deleteHistoryUseCase.delete(123) }
    }

    private fun getHistoryModel(): HistoryModel {
        return HistoryModel(
            id = 1,
            code = "code",
            shortLink = "sl",
            shortLink2 = "sl2",
            fullShortLink = "fsl",
            shareLink = "shrl",
            fullShareLink = "f_shr_l",
            isCopied = false
        )
    }
}