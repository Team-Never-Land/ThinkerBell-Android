package com.neverland.data.datasourceImpl

import com.neverland.data.datasource.AlarmDataSource
import com.neverland.data.datasource.UnivDataSource
import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.alarm.AlarmDTO
import com.neverland.data.remote.model.univ.AcademicScheduleResDTO
import com.neverland.data.remote.model.univ.BannerResDTO
import com.neverland.data.remote.model.univ.DeptContactResDTO
import com.neverland.data.remote.model.univ.DeptUrlResDTO
import com.neverland.data.remote.service.AlarmService
import com.neverland.data.remote.service.UnivService
import retrofit2.Response
import javax.inject.Inject

class UnivDataSourceImpl @Inject constructor(
    private val service: UnivService
): UnivDataSource {
    override suspend fun getDeptUrl(): Response<BaseResponse<List<DeptUrlResDTO>>> {
        return service.getDeptUrl()
    }

    override suspend fun getDeptContact(): Response<BaseResponse<List<DeptContactResDTO>>> {
        return service.getDeptContact()
    }

    override suspend fun getMonthlyAcademicSchedule(
        month: Int,
        ssaId: String
    ): Response<BaseResponse<List<AcademicScheduleResDTO>>> {
        return service.getMonthlyAcademicSchedule(month = month, ssaId = ssaId)
    }

    override suspend fun getBanner(): Response<BaseResponse<List<BannerResDTO>>> {
        return service.getBanner()
    }
}