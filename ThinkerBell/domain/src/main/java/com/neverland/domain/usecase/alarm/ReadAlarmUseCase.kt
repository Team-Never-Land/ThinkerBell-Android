package com.neverland.domain.usecase.alarm

import com.neverland.domain.repository.AlarmRepository
import javax.inject.Inject


class ReadAlarmUseCase @Inject constructor(
    private val repository: AlarmRepository
){

    suspend operator fun invoke(alarmId: Int): Result<String> {
        return repository.readAlarm(alarmId)
    }
}