package com.neverland.domain.usecase.alarm

import com.neverland.domain.repository.AlarmRepository
import javax.inject.Inject


class CheckAlarmUseCase @Inject constructor(
    private val repository: AlarmRepository
) {
    suspend operator fun invoke(ssaId: String, keyword: String): Result<Boolean> {
        return repository.checkAlarm(ssaId, keyword)
    }
}