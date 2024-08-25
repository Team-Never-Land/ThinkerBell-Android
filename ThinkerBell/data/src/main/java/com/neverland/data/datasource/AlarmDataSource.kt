package com.neverland.data.datasource

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.alarm.AlarmDTO
import retrofit2.Response

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
}