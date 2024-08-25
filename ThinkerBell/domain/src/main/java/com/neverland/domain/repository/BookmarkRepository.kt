package com.neverland.domain.repository

import com.neverland.domain.model.notice.BookmarkNotice
import com.neverland.domain.model.notice.RecentBookmarkNotice

interface BookmarkRepository {

    suspend fun getNoticeBookmark(ssaId: String): Result<BookmarkNotice>

    suspend fun getRecentNoticeBookmark(ssaId: String): Result<List<RecentBookmarkNotice>>

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