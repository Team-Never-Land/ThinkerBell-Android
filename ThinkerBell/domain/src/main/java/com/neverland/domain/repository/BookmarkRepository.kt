package com.neverland.domain.repository

import com.neverland.domain.model.notice.BookmarkNotice
import com.neverland.domain.model.notice.RecentBookmarkNotice
import com.neverland.domain.model.univ.AcademicSchedule
import com.neverland.domain.model.univ.RecentAcademicSchedule

interface BookmarkRepository {

    suspend fun getNoticeBookmark(ssaId: String): Result<BookmarkNotice>

    suspend fun getScheduleBookmark(ssaId: String): Result<List<AcademicSchedule>>

    suspend fun getRecentNoticeBookmark(ssaId: String): Result<List<RecentBookmarkNotice>>

    suspend fun getRecentScheduleBookmark(ssaId: String): Result<List<RecentAcademicSchedule>>

    suspend fun postNoticeBookmark(
        category: String,
        noticeId: Int,
        ssaId: String
    ): Result<Boolean>

    suspend fun deleteNoticeBookmark(
        category: String,
        noticeId: Int,
        ssaId: String
    ): Result<Boolean>
}