package com.neverland.data.datasource

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.univ.AcademicScheduleResDTO
import com.neverland.data.remote.model.univ.BannerResDTO
import com.neverland.data.remote.model.univ.DeptContactResDTO
import com.neverland.data.remote.model.univ.DeptUrlResDTO
import retrofit2.Response

interface UnivDataSource {

    suspend fun getDeptUrl(): Response<BaseResponse<List<DeptUrlResDTO>>>

    suspend fun getDeptContact(): Response<BaseResponse<List<DeptContactResDTO>>>

    suspend fun getMonthlyAcademicSchedule(
        month: Int,
        ssaId: String
    ): Response<BaseResponse<List<AcademicScheduleResDTO>>>

    suspend fun getBanner(): Response<BaseResponse<List<BannerResDTO>>>
}