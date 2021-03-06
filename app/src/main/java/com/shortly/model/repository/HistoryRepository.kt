package com.shortly.model.repository

import com.shortly.model.datamodel.HistoryModel
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun getHistory() : Flow<List<HistoryModel>>

    suspend fun delete(id: Int)
}