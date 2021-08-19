package com.shortly.model.common

import com.shortly.model.datamodel.ErrorResponse
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandler @Inject constructor(private val retrofit: Retrofit) {
    fun parseError(responseBody: ResponseBody?): ErrorResponse {
        val converter: Converter<ResponseBody, ErrorResponse> =
            retrofit.responseBodyConverter(ErrorResponse::class.java, arrayOfNulls<Annotation>(0))
        return try {
            converter.convert(responseBody!!)!!
        } catch (e: IOException) {
            return ErrorResponse()
        }
    }
}