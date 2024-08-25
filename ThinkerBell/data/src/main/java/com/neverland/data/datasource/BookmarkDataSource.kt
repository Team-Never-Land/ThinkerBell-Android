package com.neverland.data.datasource

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.notice.BookmarkNoticeDTO
import com.neverland.data.remote.model.notice.RecentBookmarkNoticeDTO
import retrofit2.Response

interface BookmarkDataSource {

    suspend fun getNoticeBookmark(
        ssaId: String
    ): Response<BaseResponse<BookmarkNoticeDTO>>

    suspend fun getRecentNoticeBookmark(
        ssaId: String
    ): Response<BaseResponse<List<RecentBookmarkNoticeDTO>>>

    suspend fun postNoticeBookmark(
        category: String,
        noticeId: Int,
        ssaId: String
    ): Response<BaseResponse<String>>

    suspend fun deleteNoticeBookmark(
        category: String,
        noticeId: Int,
        ssaId: String
    ): Response<BaseResponse<String>>
}