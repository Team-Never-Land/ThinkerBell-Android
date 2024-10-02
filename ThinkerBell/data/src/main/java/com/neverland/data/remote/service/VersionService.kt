package com.neverland.data.remote.service

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.ForceUpdateMinVersionResponse
import retrofit2.Response
import retrofit2.http.GET

interface VersionService {

    @GET("/api/version")
    suspend fun getForceUpdateMinVersion(): Response<BaseResponse<ForceUpdateMinVersionResponse>>
}