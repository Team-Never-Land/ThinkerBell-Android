package com.neverland.data.datasource

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.report.ErrorReportRequest
import retrofit2.Response

interface ReportDataSource {

    suspend fun postErrorReport(
        body: ErrorReportRequest
    ): Response<BaseResponse<String>>
}