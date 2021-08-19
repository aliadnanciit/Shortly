package com.shortly.model.datamodel

data class HistoryModel(
    val id : Int,
    val code: String = "",
    val shortLink: String = "",
    val shortLink2: String = "",
    val fullShortLink2: String = "",
    val fullShortLink: String = "",
    val shareLink: String = "",
    val fullShareLink: String = "",
    val originalLink: String = "",
    var isCopied: Boolean = false
)