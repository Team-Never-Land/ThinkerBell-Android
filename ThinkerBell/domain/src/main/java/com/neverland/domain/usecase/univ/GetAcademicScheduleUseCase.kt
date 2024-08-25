package com.neverland.domain.usecase.univ

import com.neverland.domain.model.univ.AcademicSchedule
import com.neverland.domain.repository.UnivRepository
import javax.inject.Inject

class GetAcademicScheduleUseCase @Inject constructor(
    private val repository: UnivRepository
) {

    suspend operator fun invoke(month: Int, ssaId: String): Result<List<AcademicSchedule>> {
        return repository.getMonthlyAcademicSchedule(month, ssaId)
    }
}