package com.neverland.data.repository

import com.neverland.data.datasource.BookmarkDataSource
import com.neverland.domain.model.notice.BookmarkNotice
import com.neverland.domain.model.notice.NoticeItem
import com.neverland.domain.model.notice.RecentBookmarkNotice
import com.neverland.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val dataSource: BookmarkDataSource
): BookmarkRepository {

    override suspend fun getNoticeBookmark(ssaId: String): Result<BookmarkNotice> {
        return try {
            val res = dataSource.getNoticeBookmark(ssaId = ssaId).body()

            if(res!!.code == 200){
                if(res.data != null){
                    val data = res.data
                    val bookmarkNotice = BookmarkNotice(
                        safetyNotice = data.safetyNotice?.map{NoticeItem.CommonNotice(id = it.id, title = it.title, pubDate = it.pubDate, url = it.url, marked = it.marked)},
                        revisionNotice = data.revisionNotice?.map{NoticeItem.CommonNotice(id = it.id, title = it.title, pubDate = it.pubDate, url = it.url, marked = it.marked)},
                        libraryNotice = data.libraryNotice?.map{NoticeItem.CommonNotice(id = it.id, title = it.title, pubDate = it.pubDate, url = it.url, marked = it.marked, important = it.important?:false, campus = it.campus)},
                        dormitoryNotice = data.dormitoryNotice?.map{NoticeItem.CommonNotice(id = it.id, title = it.title, pubDate = it.pubDate, url = it.url, marked = it.marked, important = it.important?:false, campus = it.campus)},
                        teachingNotice = data.teachingNotice?.map{NoticeItem.CommonNotice(id = it.id, title = it.title, pubDate = it.pubDate, url = it.url, marked = it.marked, important = it.important?:false)},
                        jobTrainingNotice = data.jobTrainingNotice?.map{NoticeItem.JobNotice(id = it.id, company = it.company, year = it.year, semester = it.semester, recruitingNum = it.recrutingNum, major = it.major, deadline = it.deadline, period = it.period, jobName = it.jobName, marked = it.marked)},
                        eventNotice = data.eventNotice?.map{NoticeItem.CommonNotice(id = it.id, title = it.title, pubDate = it.pubDate, url = it.url, marked = it.marked)},
                        studentActsNotice = data.studentActsNotice?.map{NoticeItem.CommonNotice(id = it.id, title = it.title, pubDate = it.pubDate, url = it.url, marked = it.marked)},
                        academicNotice = data.academicNotice?.map{NoticeItem.CommonNotice(id = it.id, title = it.title, pubDate = it.pubDate, url = it.url, marked = it.marked, important = it.important?:false)},
                        careerNotice = data.careerNotice?.map{NoticeItem.CommonNotice(id = it.id, title = it.title, pubDate = it.pubDate, url = it.url, marked = it.marked)},
                        biddingNotice = data.biddingNotice?.map{NoticeItem.CommonNotice(id = it.id, title = it.title, pubDate = it.pubDate, url = it.url, marked = it.marked)},
                        dormitoryEntryNotice = data.dormitoryEntryNotice?.map{NoticeItem.CommonNotice(id = it.id, title = it.title, pubDate = it.pubDate, url = it.url, marked = it.marked, important = it.important?:false, campus = it.campus)},
                        scholarshipNotice = data.scholarshipNotice?.map{NoticeItem.CommonNotice(id = it.id, title = it.title, pubDate = it.pubDate, url = it.url, marked = it.marked)},
                        normalNotice = data.normalNotice?.map{NoticeItem.CommonNotice(id = it.id, title = it.title, pubDate = it.pubDate, url = it.url, marked = it.marked)},
                    )
                    Result.success(bookmarkNotice)
                } else {
                    Result.failure(Exception("Get BookmarkNotices failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get BookmarkNotices failed: ${res.message}"))
            }
        } catch (e : Exception){
            Result.failure(e)
        }
    }

    override suspend fun getRecentNoticeBookmark(ssaId: String): Result<List<RecentBookmarkNotice>> {
        return try {
            val res = dataSource.getRecentNoticeBookmark(ssaId).body()

            if(res!!.code == 200){
                if(res.data != null) {
                    if(res.data.isNotEmpty()) {
                        Result.success(res.data.map { RecentBookmarkNotice(category = it.category, title = it.title, pubDate = it.pubDate, url = it.url) })
                    } else {
                        Result.success(emptyList())
                    }
                } else {
                    Result.failure(Exception("Get Recent Bookmark failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get Recent Bookmark failed: ${res.message}"))
            }
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postNoticeBookmark(
        category: String,
        noticeId: Int,
        ssaId: String
    ): Result<Boolean> {
        return try {
            val res = dataSource.postNoticeBookmark(category, noticeId, ssaId).body()

            if(res!!.code == 200){
                Result.success(true)
            } else {
                Result.failure(Exception("Post Bookmark failed: ${res.message}"))
            }
        } catch (e : Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteNoticeBookmark(
        category: String,
        noticeId: Int,
        ssaId: String
    ): Result<Boolean> {
        return try {
            val res = dataSource.deleteNoticeBookmark(category, noticeId, ssaId).body()

            if(res!!.code == 200){
                Result.success(true)
            } else {
                Result.failure(Exception("Delete Bookmark failed: ${res.message}"))
            }
        } catch (e : Exception){
            Result.failure(e)
        }
    }

}