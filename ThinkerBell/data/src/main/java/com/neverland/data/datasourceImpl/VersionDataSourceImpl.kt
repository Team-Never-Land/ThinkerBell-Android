package com.neverland.data.datasourceImpl

import com.neverland.data.datasource.VersionDataSource
import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.ForceUpdateMinVersionResponse
import com.neverland.data.remote.service.VersionService
import retrofit2.Response
import javax.inject.Inject

class VersionDataSourceImpl @Inject constructor(
    private val service: VersionService
): VersionDataSource {

    override suspend fun getForceUpdateMinVersion(): Response<BaseResponse<ForceUpdateMinVersionResponse>> {
        return service.getForceUpdateMinVersion()
    }
}