package com.neverland.data.utils

import com.google.gson.Gson
import com.neverland.data.remote.model.BaseResponse
import retrofit2.Response

suspend inline fun <T, R> handleResponse(
    dataNullSafe: Boolean = false,
    crossinline call: suspend () -> Response<BaseResponse<T>>,
    crossinline onSuccess: (T?) -> R
): Result<R> {
    return try {
        val response = call()

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                if (dataNullSafe) {
                    Result.success(onSuccess(null))
                } else {
                    body.data?.let {
                        Result.success(onSuccess(it))
                    } ?: Result.failure(Exception("Data is null"))
                }
            } else {
                Result.failure(Exception("Response body is null"))
            }
        } else {
            Result.failure(
                Exception(
                    parseErrorResponse(response)?.data.toString().ifEmpty { "Unknown error" })
            )
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}

fun parseErrorResponse(response: Response<*>): BaseResponse<*>? {
    val errorBody = response.errorBody()?.string()
    return if (!errorBody.isNullOrEmpty()) {
        Gson().fromJson(errorBody, BaseResponse::class.java)
    } else {
        null
    }
}
