package com.neverland.data.datasource

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.user.PostUserInfoReqDTO
import retrofit2.Response

interface UserDataSource {

    suspend fun postUserInfo(
        body: PostUserInfoReqDTO
    ): Response<BaseResponse<String>>
}