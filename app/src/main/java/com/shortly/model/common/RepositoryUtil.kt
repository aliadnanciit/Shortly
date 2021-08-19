package com.shortly.model.common

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Response<T> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            Response.success(response.body()!!)
        }
        else {
            Response.error(response.code(), response.errorBody()!!)
        }

    } catch (exception: SocketTimeoutException) {
        Timber.e(exception)
        Response.error(404, "Socket timeout exception".toResponseBody())
    } catch (throwable: Throwable) {
        Timber.e(throwable)
        Response.error(500, "Internal server error.".toResponseBody())
    }
}