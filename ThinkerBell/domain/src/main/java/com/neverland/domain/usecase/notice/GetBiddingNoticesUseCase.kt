package com.neverland.domain.usecase.notice

import com.neverland.domain.model.PageableNotice
import com.neverland.domain.model.notice.NoticeItem
import com.neverland.domain.repository.NoticeRepository
import javax.inject.Inject


class GetBiddingNoticesUseCase @Inject constructor(
    private val repository: NoticeRepository
){

    suspend operator fun invoke(page: Int, ssaId: String): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return repository.getBiddingNotices(page = page, ssaId = ssaId)
    }
}