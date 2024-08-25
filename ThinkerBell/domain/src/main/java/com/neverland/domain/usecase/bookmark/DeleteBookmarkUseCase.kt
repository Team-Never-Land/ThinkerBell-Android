package com.neverland.domain.usecase.bookmark

import com.neverland.domain.enums.NoticeType
import com.neverland.domain.repository.BookmarkRepository
import javax.inject.Inject

class DeleteBookmarkUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {

    suspend operator fun invoke(ssaId: String, category: NoticeType, noticeId: Int): Result<Boolean> {
        return repository.deleteNoticeBookmark(ssaId = ssaId, category = category.enName, noticeId = noticeId)
    }
}