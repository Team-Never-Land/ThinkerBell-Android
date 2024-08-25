package com.neverland.domain.usecase.bookmark

import com.neverland.domain.model.notice.RecentBookmarkNotice
import com.neverland.domain.repository.BookmarkRepository
import javax.inject.Inject


class GetRecentBookmarkNoticeUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {

    suspend operator fun invoke(ssaId: String): Result<List<RecentBookmarkNotice>> {
        return repository.getRecentNoticeBookmark(ssaId)
    }
}