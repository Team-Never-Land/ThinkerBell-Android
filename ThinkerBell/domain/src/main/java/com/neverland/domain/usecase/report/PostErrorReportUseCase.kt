package com.neverland.domain.usecase.report

import com.neverland.domain.repository.ReportRepository
import javax.inject.Inject

class PostErrorReportUseCase @Inject constructor(
    private val repository: ReportRepository
) {

    suspend operator fun invoke(errorMessage: String): Result<Boolean> {
        return repository.postErrorReport(errorMessage)
    }
}