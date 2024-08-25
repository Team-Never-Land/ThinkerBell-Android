package com.neverland.domain.usecase.bookmark

import com.neverland.domain.enums.NoticeType
import com.neverland.domain.repository.BookmarkRepository
import javax.inject.Inject


class PostBookmarkUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {

    suspend operator fun invoke(ssaId: String, category: NoticeType, noticeId: Int): Result<Boolean> {
        return repository.postNoticeBookmark(ssaId = ssaId, category = category.enName, noticeId = noticeId)
    }
}