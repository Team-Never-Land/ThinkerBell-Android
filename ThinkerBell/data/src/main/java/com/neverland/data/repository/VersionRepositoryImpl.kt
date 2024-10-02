package com.neverland.data.repository

import com.neverland.data.datasource.AlarmDataSource
import com.neverland.data.datasource.VersionDataSource
import com.neverland.data.utils.handleResponse
import com.neverland.domain.model.alarm.Alarm
import com.neverland.domain.repository.AlarmRepository
import com.neverland.domain.repository.VersionRepository
import javax.inject.Inject

class VersionRepositoryImpl @Inject constructor(
    private val datasource: VersionDataSource
) : VersionRepository {

    override suspend fun getForceUpdateMinVersion(): Result<String> {
        return handleResponse(
            call = { datasource.getForceUpdateMinVersion() },
            onSuccess = { data ->
                data?.versionCode ?: "0"
            }
        )
    }
}
