package com.neverland.domain.repository


interface ReportRepository {

    suspend fun postErrorReport(errorMessage: String): Result<Boolean>
}