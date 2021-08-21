package com.shortly.model.service

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.shortly.model.database.HistoryDao
import com.shortly.model.database.HistoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalPagingApi
class ShortlyDataSource @Inject constructor(
    private val historyDao: HistoryDao,
    private val ioDispatcher: CoroutineDispatcher
) : RemoteMediator<Int, HistoryEntity>() {

    @ExperimentalPagingApi
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HistoryEntity>
    ): MediatorResult {
        val key = when (loadType) {
            LoadType.REFRESH -> {
                0
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val count = withContext(ioDispatcher) {
                    historyDao.count()?.size ?: 0
                }
//                val lastItem = state.lastItemOrNull()
//                if(lastItem == null) {
//                    return MediatorResult.Success(endOfPaginationReached = true)
//                }
//                lastItem.id
                count
            }
        }

        val list = buildList(key ?: 0)
        if(loadType == LoadType.REFRESH) {
            historyDao.deleteAll()
        }
        historyDao.insertAll(list)

        return MediatorResult.Success(
            endOfPaginationReached = key >= 200
        )
    }

    private fun buildList(id: Int) : List<HistoryEntity> {
        val list = mutableListOf<HistoryEntity>()
        for(i in 0..4) {
            list.add(createEntities(id+i))
        }
        return list
    }
    private fun createEntities(id: Int): HistoryEntity {
        return HistoryEntity(
            id = id,
            shortLink = "$id shortLink",
            shortLink2 = "$id shortLink2",
            fullShortLink = "$id fullShortLink",
            fullShortLink2 = "$id fullShortLink2",
            shareLink = "$id shareLink",
            fullShareLink = "$id fullShortLink",
            originalLink = "$id originalLink"
        )
    }
}