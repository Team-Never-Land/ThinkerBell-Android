package com.neverland.data.datasourceImpl

import com.neverland.data.datasource.BookmarkDataSource
import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.notice.BookmarkNoticeDTO
import com.neverland.data.remote.model.notice.RecentBookmarkNoticeDTO
import com.neverland.data.remote.model.univ.AcademicScheduleResDTO
import com.neverland.data.remote.model.univ.RecentBookmarkScheduleResDTO
import com.neverland.data.remote.service.BookmarkService
import retrofit2.Response
import javax.inject.Inject

class BookmarkDataSourceImpl @Inject constructor(
    private val service: BookmarkService
): BookmarkDataSource {
    override suspend fun getNoticeBookmark(ssaId: String): Response<BaseResponse<BookmarkNoticeDTO>> {
        return service.getNoticeBookmark(ssaId)
    }

    override suspend fun getScheduleBookmark(ssaId: String): Response<BaseResponse<List<AcademicScheduleResDTO>>> {
        return service.getScheduleBookmark(ssaId)
    }

    override suspend fun getRecentNoticeBookmark(ssaId: String): Response<BaseResponse<List<RecentBookmarkNoticeDTO>>> {
        return service.getRecentNoticeBookmark(ssaId)
    }

    override suspend fun getRecentScheduleBookmark(ssaId: String): Response<BaseResponse<List<RecentBookmarkScheduleResDTO>>> {
        return service.getRecentScheduleBookmark(ssaId)
    }

    override suspend fun postNoticeBookmark(
        category: String,
        noticeId: Int,
        ssaId: String
    ): Response<BaseResponse<String>> {
        return service.postNoticeBookmark(category = category, noticeId = noticeId, ssaId = ssaId)
    }

    override suspend fun deleteNoticeBookmark(
        category: String,
        noticeId: Int,
        ssaId: String
    ): Response<BaseResponse<String>> {
        return service.deleteNoticeBookmark(category = category, noticeId = noticeId, ssaId = ssaId)
    }
}