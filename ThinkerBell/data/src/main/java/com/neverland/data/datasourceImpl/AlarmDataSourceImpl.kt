package com.neverland.data.datasourceImpl

import com.neverland.data.datasource.AlarmDataSource
import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.alarm.AlarmDTO
import com.neverland.data.remote.service.AlarmService
import retrofit2.Response
import javax.inject.Inject

class AlarmDataSourceImpl @Inject constructor(
    private val service: AlarmService
): AlarmDataSource {

    override suspend fun readAlarm(alarmId: Int): Response<BaseResponse<String>> {
        return service.readAlarm(alarmId)
    }

    override suspend fun getAlarm(
        ssaId: String,
        keyword: String
    ): Response<BaseResponse<List<AlarmDTO>>> {
        return service.getAlarm(ssaId, keyword)
    }

    override suspend fun checkAlarm(
        ssaId: String,
        keyword: String
    ): Response<BaseResponse<Boolean>> {
        return service.checkAlarm(ssaId, keyword)
    }

    override suspend fun checkAllAlarm(ssaId: String): Response<BaseResponse<Boolean>> {
        return service.checkAllAlarm(ssaId)
    }

    override suspend fun getAlarmStatus(ssaId: String): Response<BaseResponse<Boolean>> {
        return service.getAlarmStatus(ssaId)
    }

    override suspend fun patchAlarmStatus(ssaId: String): Response<BaseResponse<Boolean>> {
        return service.patchAlarmStatus(ssaId)
    }
}