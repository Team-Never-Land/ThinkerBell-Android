package com.neverland.data.repository

import com.neverland.data.datasource.BookmarkDataSource
import com.neverland.data.utils.handleResponse
import com.neverland.domain.model.notice.BookmarkNotice
import com.neverland.domain.model.notice.NoticeItem
import com.neverland.domain.model.notice.RecentBookmarkNotice
import com.neverland.domain.model.univ.AcademicSchedule
import com.neverland.domain.model.univ.RecentAcademicSchedule
import com.neverland.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val dataSource: BookmarkDataSource
) : BookmarkRepository {

    override suspend fun getNoticeBookmark(ssaId: String): Result<BookmarkNotice> {
        return handleResponse(
            call = { dataSource.getNoticeBookmark(ssaId) },
            onSuccess = { data ->
                BookmarkNotice(
                    safetyNotice = data?.safetyNotice?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked
                        )
                    },
                    revisionNotice = data?.revisionNotice?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked
                        )
                    },
                    libraryNotice = data?.libraryNotice?.map {
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
                    dormitoryNotice = data?.dormitoryNotice?.map {
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
                    teachingNotice = data?.teachingNotice?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked,
                            important = it.important ?: false
                        )
                    },
                    eventNotice = data?.eventNotice?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked
                        )
                    },
                    studentActsNotice = data?.studentActsNotice?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked
                        )
                    },
                    academicNotice = data?.academicNotice?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked,
                            important = it.important ?: false
                        )
                    },
                    careerNotice = data?.careerNotice?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked
                        )
                    },
                    biddingNotice = data?.biddingNotice?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked
                        )
                    },
                    dormitoryEntryNotice = data?.dormitoryEntryNotice?.map {
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
                    scholarshipNotice = data?.scholarshipNotice?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            title = it.title,
                            pubDate = it.pubDate,
                            url = it.url,
                            marked = it.marked
                        )
                    },
                    normalNotice = data?.normalNotice?.map {
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

    override suspend fun getScheduleBookmark(ssaId: String): Result<List<AcademicSchedule>> {
        return handleResponse(
            dataNullSafe = false,
            call = { dataSource.getScheduleBookmark(ssaId) },
            onSuccess = { data ->
                data?.takeIf { it.isNotEmpty() }?.map {
                    AcademicSchedule(
                        id = it.id,
                        title = it.title,
                        startDate = it.startDate,
                        endDate = it.endDate,
                        marked = it.marked
                    )
                } ?: emptyList()
            }
        )
    }

    override suspend fun getRecentNoticeBookmark(ssaId: String): Result<List<RecentBookmarkNotice>> {
        return handleResponse(
            dataNullSafe = false,
            call = { dataSource.getRecentNoticeBookmark(ssaId) },
            onSuccess = { data ->
                data?.takeIf { it.isNotEmpty() }?.map {
                    RecentBookmarkNotice(
                        category = it.category,
                        title = it.title,
                        pubDate = it.pubDate,
                        url = it.url
                    )
                } ?: emptyList()
            }
        )
    }

    override suspend fun getRecentScheduleBookmark(ssaId: String): Result<List<RecentAcademicSchedule>> {
        return handleResponse(
            dataNullSafe = false,
            call = { dataSource.getRecentScheduleBookmark(ssaId) },
            onSuccess = { data ->
                data?.takeIf { it.isNotEmpty() }?.map {
                    RecentAcademicSchedule(
                        id = it.id,
                        title = it.title,
                        startDate = it.startDate,
                        endDate = it.endDate
                    )
                } ?: emptyList()
            }
        )
    }

    override suspend fun postNoticeBookmark(
        category: String,
        noticeId: Int,
        ssaId: String
    ): Result<Boolean> {
        return handleResponse(
            dataNullSafe = true,
            call = { dataSource.postNoticeBookmark(category, noticeId, ssaId) },
            onSuccess = { true }
        )
    }


    override suspend fun deleteNoticeBookmark(
        category: String,
        noticeId: Int,
        ssaId: String
    ): Result<Boolean> {
        return handleResponse(
            dataNullSafe = true,
            call = { dataSource.deleteNoticeBookmark(category, noticeId, ssaId) },
            onSuccess = { true }
        )
    }
}