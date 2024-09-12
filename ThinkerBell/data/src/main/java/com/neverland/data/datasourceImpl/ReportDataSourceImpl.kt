package com.neverland.data.datasourceImpl

import com.neverland.data.datasource.ReportDataSource
import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.report.ErrorReportRequest
import com.neverland.data.remote.service.ReportService
import retrofit2.Response
import javax.inject.Inject

class ReportDataSourceImpl @Inject constructor(
    private val service: ReportService
): ReportDataSource {

    override suspend fun postErrorReport(body: ErrorReportRequest): Response<BaseResponse<String>> {
        return service.postErrorReport(body)
    }
}