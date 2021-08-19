package com.shortly.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    val code: String = "",
    val shortLink: String = "",
    val shortLink2: String = "",
    val fullShortLink2: String = "",
    val fullShortLink: String = "",
    val shareLink: String = "",
    val fullShareLink: String = "",
    val originalLink: String = "",
)