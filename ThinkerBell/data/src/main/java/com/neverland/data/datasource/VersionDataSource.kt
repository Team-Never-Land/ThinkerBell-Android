package com.neverland.data.datasource

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.ForceUpdateMinVersionResponse
import com.neverland.data.remote.model.alarm.AlarmDTO
import retrofit2.Response
import retrofit2.http.PATCH

interface VersionDataSource {

    suspend fun getForceUpdateMinVersion(): Response<BaseResponse<ForceUpdateMinVersionResponse>>
}