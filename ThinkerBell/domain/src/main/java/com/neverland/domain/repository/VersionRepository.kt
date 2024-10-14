package com.neverland.domain.repository

interface VersionRepository {

    suspend fun getForceUpdateMinVersion(): Result<String>
}