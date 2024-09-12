package com.neverland.data.remote.service

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.alarm.AlarmDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface AlarmService {
    @GET("/api/alarm/mark-viewed")
    suspend fun readAlarm(
        @Query("alarmId") alarmId: Int
    ): Response<BaseResponse<String>>

    @GET("/api/alarm/get")
    suspend fun getAlarm(
        @Query("SSAID") ssaId: String,
        @Query("keyword") keyword: String
    ): Response<BaseResponse<List<AlarmDTO>>>

    @GET("/api/alarm/check")
    suspend fun checkAlarm(
        @Query("SSAID") ssaId: String,
        @Query("keyword") keyword: String
    ): Response<BaseResponse<Boolean>>

    @GET("/api/alarm/check-all")
    suspend fun checkAllAlarm(
        @Query("SSAID") ssaId: String
    ): Response<BaseResponse<Boolean>>

    @GET("/api/alarm/alarm-status")
    suspend fun getAlarmStatus(
        @Query("SSAID") ssaId: String
    ): Response<BaseResponse<Boolean>>

    @PATCH("/api/alarm/alarm-toggle")
    suspend fun patchAlarmStatus(
        @Query("SSAID") ssaId: String
    ): Response<BaseResponse<Boolean>>
}