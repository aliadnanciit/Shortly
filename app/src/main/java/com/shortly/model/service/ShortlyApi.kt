package com.shortly.model.service

import com.shortly.model.datamodel.ShortenApiResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

const val API_SHORTLY = "shorten"

interface ShortlyApi {

    @POST(API_SHORTLY)
    suspend fun createShortenUrl(
        @Query("url") url: String
    ): Response<ShortenApiResponse>
}