package com.neverland.domain.usecase.bookmark

import com.neverland.domain.model.univ.RecentAcademicSchedule
import com.neverland.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetRecentBookmarkScheduleUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(ssaId: String): Result<List<RecentAcademicSchedule>> {
        return repository.getRecentScheduleBookmark(ssaId)
    }
}