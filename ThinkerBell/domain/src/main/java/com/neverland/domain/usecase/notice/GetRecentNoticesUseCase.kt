package com.neverland.domain.usecase.notice

import com.neverland.domain.model.notice.RecentNotices
import com.neverland.domain.repository.NoticeRepository
import javax.inject.Inject

class GetRecentNoticesUseCase @Inject constructor(
    private val repository: NoticeRepository
){

    suspend operator fun invoke(ssaId: String): Result<RecentNotices> {
        return repository.getRecentNotices(ssaId = ssaId)
    }
}