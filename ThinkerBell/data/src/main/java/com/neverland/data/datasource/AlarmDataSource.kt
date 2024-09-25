package com.neverland.data.datasource

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.alarm.AlarmDTO
import retrofit2.Response
import retrofit2.http.PATCH

interface AlarmDataSource {
    suspend fun readAlarm(
        alarmId: Int
    ): Response<BaseResponse<String>>

    suspend fun getAlarm(
        ssaId: String,
        keyword: String
    ): Response<BaseResponse<List<AlarmDTO>>>

    suspend fun checkAlarm(
        ssaId: String,
        keyword: String
    ): Response<BaseResponse<Boolean>>

    suspend fun checkAllAlarm(
        ssaId: String
    ): Response<BaseResponse<Boolean>>

    suspend fun getAlarmStatus(
        ssaId: String
    ): Response<BaseResponse<Boolean>>

    @PATCH("/api/alarm/alarm-toggle")
    suspend fun patchAlarmStatus(
        ssaId: String
    ): Response<BaseResponse<Boolean>>
}