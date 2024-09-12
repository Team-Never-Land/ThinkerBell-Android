package com.neverland.data.repository

import com.neverland.data.datasource.NoticeDataSource
import com.neverland.data.utils.handleResponse
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.PageableNotice
import com.neverland.domain.model.notice.AllNotices
import com.neverland.domain.model.notice.NoticeItem
import com.neverland.domain.model.notice.RecentNotices
import com.neverland.domain.repository.NoticeRepository
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val datasource: NoticeDataSource
) : NoticeRepository {

    override suspend fun getDormitoryNotices(
        page: Int,
        ssaId: String,
        campus: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getDormitoryNotices(page, ssaId, campus) },
            onSuccess = { data ->
                PageableNotice(
                    page = data!!.page,
                    size = data.size,
                    totalItems = data.totalItems,
                    items = data.items.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            url = it.url,
                            campus = it.campus,
                            title = it.title,
                            pubDate = it.pubDate,
                            important = it.important ?: false,
                            marked = it.marked
                        )
                    }
                )
            }
        )
    }

    override suspend fun getDormitoryEntryNotices(
        page: Int,
        ssaId: String,
        campus: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getDormitoryEntryNotices(page, ssaId, campus) },
            onSuccess = { data ->
                PageableNotice(
                    page = data!!.page,
                    size = data.size,
                    totalItems = data.totalItems,
                    items = data.items.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            url = it.url,
                            campus = it.campus,
                            title = it.title,
                            pubDate = it.pubDate,
                            important = it.important ?: false,
                            marked = it.marked
                        )
                    }
                )
            }
        )
    }

    override suspend fun getJobTrainingNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.JobNotice>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getJobTrainingNotices(page, ssaId) },
            onSuccess = { data ->
                PageableNotice(
                    page = data!!.page,
                    size = data.size,
                    totalItems = data.totalItems,
                    items = data.items.map {
                        NoticeItem.JobNotice(
                            id = it.id,
                            company = it.company,
                            year = it.year,
                            semester = it.semester,
                            period = it.period,
                            major = it.major,
                            recruitingNum = it.recrutingNum,
                            deadline = it.deadline,
                            jobName = it.jobName,
                            marked = it.marked
                        )
                    }
                )
            }
        )
    }

    override suspend fun getLibraryNotices(
        page: Int,
        ssaId: String,
        campus: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getLibraryNotices(page, ssaId, campus) },
            onSuccess = { data ->
                PageableNotice(
                    page = data!!.page,
                    size = data.size,
                    totalItems = data.totalItems,
                    items = data.items.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            url = it.url,
                            campus = it.campus,
                            title = it.title,
                            pubDate = it.pubDate,
                            important = it.important ?: false,
                            marked = it.marked
                        )
                    }
                )
            }
        )
    }

    override suspend fun getTeachingNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getTeachingNotices(page, ssaId) },
            onSuccess = { data ->
                PageableNotice(
                    page = data!!.page,
                    size = data.size,
                    totalItems = data.totalItems,
                    items = data.items.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            url = it.url,
                            campus = it.campus,
                            title = it.title,
                            pubDate = it.pubDate,
                            important = it.important ?: false,
                            marked = it.marked
                        )
                    }
                )
            }
        )
    }


    override suspend fun getAcademicNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getAcademicNotices(page, ssaId) },
            onSuccess = { data ->
                PageableNotice(
                    page = data!!.page,
                    size = data.size,
                    totalItems = data.totalItems,
                    items = data.items.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            campus = it.campus,
                            important = it.important ?: false,
                            marked = it.marked
                        )
                    }
                )
            }
        )
    }

    override suspend fun getEventNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getEventNotices(page, ssaId) },
            onSuccess = { data ->
                PageableNotice(
                    page = data!!.page,
                    size = data.size,
                    totalItems = data.totalItems,
                    items = data.items.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            campus = it.campus,
                            important = it.important ?: false,
                            marked = it.marked
                        )
                    }
                )
            }
        )
    }

    override suspend fun getNormalNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getNormalNotices(page, ssaId) },
            onSuccess = { data ->
                PageableNotice(
                    page = data!!.page,
                    size = data.size,
                    totalItems = data.totalItems,
                    items = data.items.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            campus = it.campus,
                            important = it.important ?: false,
                            marked = it.marked
                        )
                    }
                )
            }
        )
    }

    override suspend fun getRevisionNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getRevisionNotices(page, ssaId) },
            onSuccess = { data ->
                PageableNotice(
                    page = data!!.page,
                    size = data.size,
                    totalItems = data.totalItems,
                    items = data.items.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            campus = it.campus,
                            important = it.important ?: false,
                            marked = it.marked
                        )
                    }
                )
            }
        )
    }

    override suspend fun getSafetyNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getSafetyNotices(page, ssaId) },
            onSuccess = { data ->
                PageableNotice(
                    page = data!!.page,
                    size = data.size,
                    totalItems = data.totalItems,
                    items = data.items.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            campus = it.campus,
                            important = it.important ?: false,
                            marked = it.marked
                        )
                    }
                )
            }
        )
    }

    override suspend fun getScholarshipNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getScholarship(page, ssaId) },
            onSuccess = { data ->
                PageableNotice(
                    page = data!!.page,
                    size = data.size,
                    totalItems = data.totalItems,
                    items = data.items.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            campus = it.campus,
                            important = it.important ?: false,
                            marked = it.marked
                        )
                    }
                )
            }
        )
    }

    override suspend fun getStudentActsNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getStudentActsNotices(page, ssaId) },
            onSuccess = { data ->
                PageableNotice(
                    page = data!!.page,
                    size = data.size,
                    totalItems = data.totalItems,
                    items = data.items.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            campus = it.campus,
                            important = it.important ?: false,
                            marked = it.marked
                        )
                    }
                )
            }
        )
    }


    override suspend fun getCareerNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return handleResponse(
            call = { datasource.getCareerNotices(page, ssaId) },
            onSuccess = { data ->
                PageableNotice(
                    page = data!!.page,
                    size = data.size,
                    totalItems = data.totalItems,
                    items = data.items.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            campus = it.campus,
                            important = it.important ?: false,
                            marked = it.marked
                        )
                    }
                )
            }
        )
    }

    override suspend fun getBiddingNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return handleResponse(
            call = { datasource.getBiddingNotices(page, ssaId) },
            onSuccess = { data ->
                PageableNotice(
                    page = data!!.page,
                    size = data.size,
                    totalItems = data.totalItems,
                    items = data.items.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            campus = it.campus,
                            important = it.important ?: false,
                            marked = it.marked
                        )
                    }
                )
            }
        )
    }

    override suspend fun searchNoticesByCategory(
        noticeType: NoticeType,
        keyword: String,
        ssaId: String
    ): Result<List<NoticeItem>> {
        return handleResponse(
            call = { datasource.searchNotices(keyword = keyword, ssaId = ssaId) },
            onSuccess = { data ->
                val notices = when (noticeType) {
                    NoticeType.NORMAL_NOTICE -> data?.normalNotices
                    NoticeType.EVENT_NOTICE -> data?.eventNotices
                    NoticeType.ACADEMIC_NOTICE -> data?.academicNotices
                    NoticeType.SCHOLARSHIP_NOTICE -> data?.scholarshipNotices
                    NoticeType.CAREER_NOTICE -> data?.careerNotices
                    NoticeType.STUDENT_ACTS_NOTICE -> data?.studentActsNotices
                    NoticeType.BIDDING_NOTICE -> data?.biddingNotices
                    NoticeType.SAFETY_NOTICE -> data?.safetyNotices
                    NoticeType.REVISION_NOTICE -> data?.revisionNotices
                    NoticeType.DORMITORY_NOTICE -> data?.dormitoryNotices
                    NoticeType.DORMITORY_ENTRY_NOTICE -> data?.dormitoryEntryNotices
                    NoticeType.LIBRARY_NOTICE -> data?.libraryNotices
                    NoticeType.TEACHING_NOTICE -> data?.teachingNotices
                    else -> null
                }

                notices?.map {
                    NoticeItem.CommonNotice(
                        id = it.id,
                        title = it.title,
                        pubDate = it.pubDate,
                        url = it.url,
                        campus = it.campus,
                        marked = it.marked
                    )
                } ?: emptyList()

            }
        )
    }

    override suspend fun getRecentNotices(ssaId: String): Result<RecentNotices> {
        return handleResponse(
            call = { datasource.getRecentNotices(ssaId) },
            onSuccess = { data ->
                RecentNotices(
                    academicNotices = data!!.academicNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            pubDate = it.pubDate,
                            url = it.url,
                            title = it.title
                        )
                    } ?: emptyList(),
                    careerNotices = data.careerNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            pubDate = it.pubDate,
                            url = it.url,
                            title = it.title
                        )
                    } ?: emptyList(),
                    scholarshipNotices = data.scholarshipNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            pubDate = it.pubDate,
                            url = it.url,
                            title = it.title
                        )
                    } ?: emptyList(),
                    eventNotices = data.eventNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            pubDate = it.pubDate,
                            url = it.url,
                            title = it.title
                        )
                    } ?: emptyList(),
                    normalNotices = data.normalNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            pubDate = it.pubDate,
                            url = it.url,
                            title = it.title
                        )
                    } ?: emptyList()
                )
            }
        )
    }

    override suspend fun searchAllNoticesByCategory(
        keyword: String,
        ssaId: String
    ): Result<AllNotices> {
        return handleResponse(
            call = { datasource.searchNotices(keyword = keyword, ssaId = ssaId) },
            onSuccess = { data ->
                AllNotices(
                    safetyNotice = data!!.safetyNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked
                        )
                    },
                    revisionNotice = data.revisionNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked
                        )
                    },
                    libraryNotice = data.libraryNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked,
                            important = it.important ?: false,
                            campus = it.campus
                        )
                    },
                    dormitoryNotice = data.dormitoryNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked,
                            important = it.important ?: false,
                            campus = it.campus
                        )
                    },
                    teachingNotice = data.teachingNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked,
                            important = it.important ?: false
                        )
                    },
                    eventNotice = data.eventNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked
                        )
                    },
                    studentActsNotice = data.studentActsNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked
                        )
                    },
                    academicNotice = data.academicNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked,
                            important = it.important ?: false
                        )
                    },
                    careerNotice = data.careerNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked
                        )
                    },
                    biddingNotice = data.biddingNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked
                        )
                    },
                    dormitoryEntryNotice = data.dormitoryEntryNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked,
                            important = it.important ?: false,
                            campus = it.campus
                        )
                    },
                    scholarshipNotice = data.scholarshipNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked
                        )
                    },
                    normalNotice = data.normalNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked
                        )
                    }
                )
            }
        )
    }

}