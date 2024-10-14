package com.neverland.data.repository

import com.neverland.data.datasource.AlarmDataSource
import com.neverland.data.utils.handleResponse
import com.neverland.domain.model.alarm.Alarm
import com.neverland.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val datasource: AlarmDataSource
) : AlarmRepository {

    override suspend fun readAlarm(alarmId: Int): Result<String> {
        return handleResponse(
            call = { datasource.readAlarm(alarmId) },
            onSuccess = { it!! }
        )
    }

    override suspend fun getAlarm(ssaId: String, keyword: String): Result<List<Alarm>> {
        return handleResponse(
            call = { datasource.getAlarm(ssaId, keyword) },
            onSuccess = { data ->
                data?.map {
                    Alarm(
                        id = it.id,
                        categoryId = it.categoryId,
                        title = it.title,
                        noticeTypeKorean = it.noticeTypeKorean,
                        noticeTypeEnglish = it.noticeTypeEnglish,
                        viewed = it.viewed,
                        pubDate = it.pubDate?:"",
                        url = it.url?:"https://www.mju.ac.kr/mjukr/index.do",
                        marked = it.marked
                    )
                } ?: emptyList()
            }
        )
    }

    override suspend fun checkAlarm(ssaId: String, keyword: String): Result<Boolean> {
        return handleResponse(
            call = { datasource.checkAlarm(ssaId, keyword) },
            onSuccess = { it!! }
        )
    }

    override suspend fun checkAllAlarm(ssaId: String): Result<Boolean> {
        return handleResponse(
            call = { datasource.checkAllAlarm(ssaId) },
            onSuccess = { it!! }
        )
    }

    override suspend fun getAlarmStatus(ssaId: String): Result<Boolean> {
        return handleResponse(
            call = { datasource.getAlarmStatus(ssaId) },
            onSuccess = { it!! }
        )
    }

    override suspend fun patchAlarmStatus(ssaId: String): Result<Boolean> {
        return handleResponse(
            call = { datasource.patchAlarmStatus(ssaId) },
            onSuccess = { true }
        )
    }
}
