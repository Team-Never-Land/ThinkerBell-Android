package com.neverland.data.datasource

import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.PageableResponse
import com.neverland.data.remote.model.notice.CommonNoticeDTO
import com.neverland.data.remote.model.notice.JobTrainingNoticeDTO
import com.neverland.data.remote.model.notice.SearchNoticeResultDTO
import retrofit2.Response

interface NoticeDataSource {
    suspend fun searchNotices(
        keyword: String,
        ssaId: String
    ): Response<BaseResponse<SearchNoticeResultDTO>>
    
    suspend fun getRecentNotices(
        ssaId: String
    ): Response<BaseResponse<SearchNoticeResultDTO>>

    suspend fun getDormitoryNotices(
        page: Int,
        ssaId: String,
        campus: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>>

    suspend fun getDormitoryEntryNotices(
        page: Int,
        ssaId: String,
        campus: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>>

    suspend fun getJobTrainingNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<JobTrainingNoticeDTO>>>

    suspend fun getLibraryNotices(
        page: Int,
        ssaId: String,
        campus: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>>

    // 학교 공지
    suspend fun getTeachingNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>>

    suspend fun getAcademicNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>>

    suspend fun getEventNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>>

    suspend fun getNormalNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>>

    suspend fun getRevisionNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>>

    suspend fun getSafetyNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>>

    suspend fun getScholarship(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>>

    suspend fun getStudentActsNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>>

    suspend fun getCareerNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>>

    suspend fun getBiddingNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>>
}