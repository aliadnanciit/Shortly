package com.shortly.model.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.shortly.model.database.HistoryDao
import com.shortly.model.database.HistoryEntity
import com.shortly.model.datamodel.HistoryModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
    @Named("IO_DISPATCHER") private val ioDispatcher: CoroutineDispatcher
) : HistoryRepository {

    override suspend fun delete(id: Int) {
        withContext(ioDispatcher) {
            historyDao.delete(id)
        }
    }

    override suspend fun getHistory(): Flow<List<HistoryModel>> {
        return withContext(ioDispatcher) {
            historyDao.getHistory()
                .map { list ->
                    list.map { d ->
                        convert(d)
                    }
                }
        }
    }

    override suspend fun getPagingHistory(): Flow<PagingData<HistoryModel>> {
        return withContext(ioDispatcher) {
            Pager(PagingConfig(5)) {
                historyDao.getPagingHistory()
            }.flow
                .map {
                    it.map {
                        convert(it)
                    }
                }
        }
    }

    private fun convert(historyEntity: HistoryEntity): HistoryModel {
        return HistoryModel(
            id = historyEntity.id!!,
            code = historyEntity.code,
            shortLink = historyEntity.shortLink,
            fullShortLink = historyEntity.fullShortLink,
            shortLink2 = historyEntity.shortLink2,
            fullShortLink2 = historyEntity.fullShortLink2,
            shareLink = historyEntity.shareLink,
            fullShareLink = historyEntity.fullShareLink,
            originalLink = historyEntity.originalLink
        )
    }
}