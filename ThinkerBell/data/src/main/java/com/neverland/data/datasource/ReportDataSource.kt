package com.neverland.data.datasource

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.report.ErrorReportRequest
import com.neverland.data.remote.model.user.PostUserInfoReqDTO
import retrofit2.Response

interface ReportDataSource {

    suspend fun postErrorReport(
        body: ErrorReportRequest
    ): Response<BaseResponse<String>>
}