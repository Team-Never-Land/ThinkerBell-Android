package com.neverland.domain.usecase.notice

import com.neverland.domain.model.PageableNotice
import com.neverland.domain.model.notice.NoticeItem
import com.neverland.domain.repository.NoticeRepository
import javax.inject.Inject


class GetDormitoryEntryNoticesUseCase @Inject constructor(
    private val repository: NoticeRepository
){

    suspend operator fun invoke(page: Int, ssaId: String, campus: String): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return repository.getDormitoryEntryNotices(page = page, ssaId = ssaId, campus = campus)
    }
}