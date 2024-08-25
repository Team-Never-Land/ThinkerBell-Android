package com.neverland.domain.usecase.notice

import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.notice.NoticeItem
import com.neverland.domain.repository.NoticeRepository
import javax.inject.Inject

class SearchNoticesByCategoryUseCase @Inject constructor(
    private val repository: NoticeRepository
){

    suspend operator fun invoke(noticeType: NoticeType, keyword: String, ssaId: String): Result<List<NoticeItem>> {
        return repository.searchNoticesByCategory(noticeType = noticeType, keyword = keyword, ssaId = ssaId)
    }
}