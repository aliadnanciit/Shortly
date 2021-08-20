package com.shortly.model.usecase

import androidx.paging.PagingData
import com.shortly.model.datamodel.HistoryModel
import com.shortly.model.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {

    suspend fun getHistory() : Flow<List<HistoryModel>> {
        return historyRepository.getHistory()
    }

    suspend fun getPagingHistory() : Flow<PagingData<HistoryModel>> {
        return historyRepository.getPagingHistory()
    }
}