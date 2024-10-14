package com.neverland.data.datasource

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.ForceUpdateMinVersionResponse
import retrofit2.Response

interface VersionDataSource {

    suspend fun getForceUpdateMinVersion(): Response<BaseResponse<ForceUpdateMinVersionResponse>>
}