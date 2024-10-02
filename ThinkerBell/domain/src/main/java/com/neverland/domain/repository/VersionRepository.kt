package com.neverland.domain.repository

import com.neverland.domain.model.alarm.Alarm

interface VersionRepository {

    suspend fun getForceUpdateMinVersion(): Result<String>
}