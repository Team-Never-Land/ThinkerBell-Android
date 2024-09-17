package com.neverland.domain.usecase.bookmark

import com.neverland.domain.model.univ.AcademicSchedule
import com.neverland.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetBookmarkScheduleUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(ssaId: String): Result<List<AcademicSchedule>> {
        return repository.getScheduleBookmark(ssaId)
    }
}