package com.shortly.model.usecase

import com.shortly.model.repository.HistoryRepository
import javax.inject.Inject

class DeleteHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {

    suspend fun delete(id: Int) {
        return historyRepository.delete(id)
    }
}