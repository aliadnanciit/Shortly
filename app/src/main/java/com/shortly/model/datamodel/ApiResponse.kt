package com.shortly.model.datamodel

import com.squareup.moshi.Json

data class ShortenApiResponse(
    @Json(name = "result")
    val shortURL: ShortURL,
    @Json(name = "ok")
    val ok: Boolean = false,
)


data class ShortURL(
    @Json(name = "code")
    val code: String = "",
    @Json(name = "short_link")
    val shortLink: String = "",
    @Json(name = "short_link2")
    val shortLink2: String = "",
    @Json(name = "full_short_link2")
    val fullShortLink2: String = "",
    @Json(name = "full_short_link")
    val fullShortLink: String = "",
    @Json(name = "share_link")
    val shareLink: String = "",
    @Json(name = "full_share_link")
    val fullShareLink: String = "",
    @Json(name = "original_link")
    val originalLink: String = "",
)

data class ErrorResponse(
    @Json(name = "ok")
    val ok: Boolean = false,
    @Json(name = "error_code")
    val errorCode: Int = -1,
    @Json(name = "error")
    val message: String = "some server error",
)