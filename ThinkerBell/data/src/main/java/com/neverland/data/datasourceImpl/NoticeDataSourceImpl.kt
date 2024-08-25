package com.neverland.data.datasourceImpl

import com.neverland.data.datasource.NoticeDataSource
import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.PageableResponse
import com.neverland.data.remote.model.notice.CommonNoticeDTO
import com.neverland.data.remote.model.notice.JobTrainingNoticeDTO
import com.neverland.data.remote.model.notice.SearchNoticeResultDTO
import com.neverland.data.remote.service.NoticeService
import retrofit2.Response
import javax.inject.Inject

class NoticeDataSourceImpl @Inject constructor(
    private val service: NoticeService
): NoticeDataSource {
    override suspend fun searchNotices(
        keyword: String,
        ssaId: String
    ): Response<BaseResponse<SearchNoticeResultDTO>> {
        return service.searchNotices(keyword = keyword, ssaId = ssaId)
    }

    override suspend fun getRecentNotices(ssaId: String): Response<BaseResponse<SearchNoticeResultDTO>> {
        return service.getRecentNotices(ssaId)
    }

    override suspend fun getDormitoryNotices(
        page: Int,
        ssaId: String,
        campus: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>> {
        return service.getDormitoryNotices(page = page, ssaId = ssaId, campus = campus)
    }

    override suspend fun getDormitoryEntryNotices(
        page: Int,
        ssaId: String,
        campus: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>> {
        return service.getDormitoryEntryNotices(page = page, ssaId = ssaId, campus = campus)
    }

    override suspend fun getJobTrainingNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<JobTrainingNoticeDTO>>> {
        return service.getJobTrainingNotices(page = page, ssaId = ssaId)
    }

    override suspend fun getLibraryNotices(
        page: Int,
        ssaId: String,
        campus: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>> {
        return service.getLibraryNotices(page = page, ssaId = ssaId, campus = campus)
    }

    override suspend fun getTeachingNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>> {
        return service.getTeachingNotices(page = page, ssaId = ssaId)
    }

    override suspend fun getAcademicNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>> {
        return service.getAcademicNotices(page = page, ssaId = ssaId)
    }

    override suspend fun getEventNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>> {
        return service.getEventNotices(page = page, ssaId = ssaId)
    }

    override suspend fun getNormalNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>> {
        return service.getNormalNotices(page = page, ssaId = ssaId)
    }

    override suspend fun getRevisionNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>> {
        return service.getRevisionNotices(page = page, ssaId = ssaId)
    }

    override suspend fun getSafetyNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>> {
        return service.getSafetyNotices(page = page, ssaId = ssaId)
    }

    override suspend fun getScholarship(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>> {
        return service.getScholarship(page = page, ssaId = ssaId)
    }

    override suspend fun getStudentActsNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>> {
        return service.getStudentActsNotices(page = page, ssaId = ssaId)
    }

    override suspend fun getCareerNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>> {
        return service.getCareerNotices(page = page, ssaId = ssaId)
    }

    override suspend fun getBiddingNotices(
        page: Int,
        ssaId: String
    ): Response<BaseResponse<PageableResponse<CommonNoticeDTO>>> {
        return service.getBiddingNotices(page = page, ssaId = ssaId)
    }
}