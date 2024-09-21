package com.neverland.data.remote.service

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.notice.BookmarkNoticeDTO
import com.neverland.data.remote.model.notice.RecentBookmarkNoticeDTO
import com.neverland.data.remote.model.univ.AcademicScheduleResDTO
import com.neverland.data.remote.model.univ.RecentBookmarkScheduleResDTO
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BookmarkService {

    @GET("/api/bookmark/notice")
    suspend fun getNoticeBookmark(
        @Query("ssaid") ssaId: String
    ): Response<BaseResponse<BookmarkNoticeDTO>>

    @GET("/api/bookmark/schedule")
    suspend fun getScheduleBookmark(
        @Query("ssaid") ssaId: String
    ): Response<BaseResponse<List<AcademicScheduleResDTO>>>

    // 공지사항 최근 즐겨찾기 3개 내역 조회
    @GET("/api/bookmark/recent-notice")
    suspend fun getRecentNoticeBookmark(
        @Query("ssaid") ssaId: String
    ): Response<BaseResponse<List<RecentBookmarkNoticeDTO>>>

    // 학사일정 최근 즐겨찾기 3개 내역 조회
    @GET("/api/bookmark/recent-schedule")
    suspend fun getRecentScheduleBookmark(
        @Query("ssaid") ssaId: String
    ): Response<BaseResponse<List<RecentBookmarkScheduleResDTO>>>
    
    @POST("/api/bookmark")
    suspend fun postNoticeBookmark(
        @Query("category") category: String,
        @Query("notice-id") noticeId: Int,
        @Query("ssaid") ssaId: String
    ): Response<BaseResponse<String>>

    @DELETE("/api/bookmark")
    suspend fun deleteNoticeBookmark(
        @Query("category") category: String,
        @Query("notice-id") noticeId: Int,
        @Query("ssaid") ssaId: String
    ): Response<BaseResponse<String>>
}