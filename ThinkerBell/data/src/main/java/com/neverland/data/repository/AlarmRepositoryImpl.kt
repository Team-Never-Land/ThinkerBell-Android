package com.neverland.data.repository

import com.neverland.data.datasource.AlarmDataSource
import com.neverland.data.remote.service.AlarmService
import com.neverland.domain.model.alarm.Alarm
import com.neverland.domain.repository.AlarmRepository
import org.json.JSONObject
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val datasource: AlarmDataSource
): AlarmRepository {

    override suspend fun readAlarm(alarmId: Int): Result<String> {
        return try {
            val res = datasource.readAlarm(alarmId).body()

            if(res!!.code == 200){
                Result.success(res.data ?: "")
            } else {
                Result.failure(Exception("Read Alarm failed: ${res.message}"))
            }
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAlarm(ssaId: String, keyword: String): Result<List<Alarm>> {
        return try {
            val res = datasource.getAlarm(ssaId, keyword).body()

            if(res!!.code == 200){
                if(res.data != null) {
                    if(res.data.isNotEmpty()) {
                        Result.success(res.data.map { Alarm(id = it.id, title = it.title, noticeTypeKorean = it.noticeTypeKorean, noticeTypeEnglish = it.noticeTypeEnglish, viewed = it.viewed, pubDate = it.pubDate, url = it.url, marked = it.marked) })
                    } else {
                        Result.success(emptyList())
                    }
                } else {
                    Result.failure(Exception("Get Alarm failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get Alarm failed: ${res.message}"))
            }
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun checkAlarm(ssaId: String, keyword: String): Result<Boolean> {
        return try {
            val res = datasource.checkAlarm(ssaId, keyword).body()

            if(res!!.code == 200){
                if(res.data != null) {
                    Result.success(res.data)
                } else {
                    Result.failure(Exception("Check Alarm failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Check Alarm failed: ${res.message}"))
            }
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun checkAllAlarm(ssaId: String): Result<Boolean> {
        return try {
            val res = datasource.checkAllAlarm(ssaId).body()

            if(res!!.code == 200){
                if(res.data != null) {
                    Result.success(res.data)
                } else {
                    Result.failure(Exception("Check Alarm failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Check Alarm failed: ${res.message}"))
            }
        } catch (e : Exception) {
            Result.failure(e)
        }
    }
}