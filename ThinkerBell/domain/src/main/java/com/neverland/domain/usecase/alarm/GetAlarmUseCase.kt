package com.neverland.domain.usecase.alarm

import com.neverland.domain.model.alarm.Alarm
import com.neverland.domain.repository.AlarmRepository
import javax.inject.Inject


class GetAlarmUseCase @Inject constructor(
    private val repository: AlarmRepository
){
    suspend operator fun invoke(ssaId: String, keyword: String): Result<List<Alarm>> {
        return repository.getAlarm(ssaId, keyword)
    }
}