package com.shortly.model.database

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historyEntity: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<HistoryEntity>)

    @Query("SELECT * FROM HistoryEntity ORDER BY id ASC")
    fun count(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity ORDER BY id ASC")
    fun getHistory(): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM HistoryEntity ORDER BY id ASC")
    fun getPagingHistory(): PagingSource<Int, HistoryEntity>

    @Query("DELETE FROM HistoryEntity WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM HistoryEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM HistoryEntity WHERE originalLink = :url")
    suspend fun findByUrl(url: String) : HistoryEntity?
}