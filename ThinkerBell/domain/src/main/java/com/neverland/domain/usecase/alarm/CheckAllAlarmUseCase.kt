package com.neverland.domain.usecase.alarm

import com.neverland.domain.repository.AlarmRepository
import javax.inject.Inject


class CheckAllAlarmUseCase @Inject constructor(
    private val repository: AlarmRepository
) {
    suspend operator fun invoke(ssaId: String): Result<Boolean> {
        return repository.checkAllAlarm(ssaId)
    }
}