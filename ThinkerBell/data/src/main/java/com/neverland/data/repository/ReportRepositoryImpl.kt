package com.neverland.data.repository

import com.neverland.data.datasource.ReportDataSource
import com.neverland.data.remote.model.report.ErrorReportRequest
import com.neverland.data.utils.handleResponse
import com.neverland.domain.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val datasource: ReportDataSource
): ReportRepository {

    override suspend fun postErrorReport(errorMessage: String): Result<Boolean> {
        return handleResponse(
            dataNullSafe = true,
            call = { datasource.postErrorReport(ErrorReportRequest(errorMessage = errorMessage))},
            onSuccess = { true }
        )
    }
}