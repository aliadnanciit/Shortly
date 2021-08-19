package com.shortly.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historyEntity: HistoryEntity)

    @Query("SELECT * FROM HistoryEntity ORDER BY id DESC")
    fun getHistory(): Flow<List<HistoryEntity>>

    @Query("DELETE FROM HistoryEntity WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM HistoryEntity WHERE originalLink = :url")
    suspend fun findByUrl(url: String) : HistoryEntity?
}