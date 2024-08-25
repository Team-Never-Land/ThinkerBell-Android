package com.neverland.data.remote.service

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.user.PostUserInfoReqDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("/api/user-info/save")
    suspend fun postUserInfo(
        @Body body: PostUserInfoReqDTO
    ): Response<BaseResponse<String>>
}