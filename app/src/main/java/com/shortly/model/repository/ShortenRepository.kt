package com.shortly.model.repository

import com.shortly.model.datamodel.ShortURL

interface ShortenRepository {

    suspend fun createShortenUrl(url: String): ShortURL

}