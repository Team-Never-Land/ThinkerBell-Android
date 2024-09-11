package com.neverland.data.remote.service

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.report.ErrorReportRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportService {

    @POST("/api/error-report")
    suspend fun postErrorReport(
        @Body body: ErrorReportRequest
    ): Response<BaseResponse<String>>
}