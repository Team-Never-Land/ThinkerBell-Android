package com.neverland.data.datasource

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.keyword.KeywordDTO
import retrofit2.Response

interface KeywordDataSource {

    suspend fun getKeyword(
        ssaId: String
    ): Response<BaseResponse<List<KeywordDTO>>>

    suspend fun postKeyword(
        keyword: String,
        ssaId: String
    ): Response<BaseResponse<String>>

    suspend fun deleteKeyword(
        keyword: String,
        ssaId: String
    ): Response<BaseResponse<String>>
}