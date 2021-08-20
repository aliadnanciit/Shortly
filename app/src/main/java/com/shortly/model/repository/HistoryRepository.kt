package com.shortly.model.repository

import androidx.paging.PagingData
import com.shortly.model.datamodel.HistoryModel
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun getHistory() : Flow<List<HistoryModel>>

    suspend fun getPagingHistory() : Flow<PagingData<HistoryModel>>

    suspend fun delete(id: Int)
}