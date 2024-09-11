package com.neverland.domain.usecase.bookmark

import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.notice.BookmarkNotice
import com.neverland.domain.model.notice.NoticeItem
import com.neverland.domain.repository.BookmarkRepository
import javax.inject.Inject


class GetBookmarkNoticeUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {

    suspend operator fun invoke(ssaId: String): Result<Map<NoticeType, List<NoticeItem>>> {
        val data = repository.getNoticeBookmark(ssaId).getOrNull()
        return if (data != null){
            Result.success(getNonEmptyListsWithItems(data))
        } else {
            Result.failure(Exception())
        }
    }

    private fun getNonEmptyListsWithItems(bookmarkNotice: BookmarkNotice): Map<NoticeType, List<NoticeItem>> {
        val nonEmptyListsWithItems = mutableMapOf<NoticeType, List<NoticeItem>>()

        if (!bookmarkNotice.safetyNotice.isNullOrEmpty()) {
            nonEmptyListsWithItems[NoticeType.SAFETY_NOTICE] = bookmarkNotice.safetyNotice
        }

        if (!bookmarkNotice.revisionNotice.isNullOrEmpty()) {
            nonEmptyListsWithItems[NoticeType.REVISION_NOTICE] = bookmarkNotice.revisionNotice
        }

        if (!bookmarkNotice.libraryNotice.isNullOrEmpty()) {
            nonEmptyListsWithItems[NoticeType.LIBRARY_NOTICE] = bookmarkNotice.libraryNotice
        }

        if (!bookmarkNotice.dormitoryNotice.isNullOrEmpty()) {
            nonEmptyListsWithItems[NoticeType.DORMITORY_NOTICE] = bookmarkNotice.dormitoryNotice
        }

        if (!bookmarkNotice.teachingNotice.isNullOrEmpty()) {
            nonEmptyListsWithItems[NoticeType.TEACHING_NOTICE] = bookmarkNotice.teachingNotice
        }

        if (!bookmarkNotice.eventNotice.isNullOrEmpty()) {
            nonEmptyListsWithItems[NoticeType.EVENT_NOTICE] = bookmarkNotice.eventNotice
        }

        if (!bookmarkNotice.studentActsNotice.isNullOrEmpty()) {
            nonEmptyListsWithItems[NoticeType.STUDENT_ACTS_NOTICE] = bookmarkNotice.studentActsNotice
        }

        if (!bookmarkNotice.academicNotice.isNullOrEmpty()) {
            nonEmptyListsWithItems[NoticeType.ACADEMIC_NOTICE] = bookmarkNotice.academicNotice
        }

        if (!bookmarkNotice.careerNotice.isNullOrEmpty()) {
            nonEmptyListsWithItems[NoticeType.CAREER_NOTICE] = bookmarkNotice.careerNotice
        }

        if (!bookmarkNotice.biddingNotice.isNullOrEmpty()) {
            nonEmptyListsWithItems[NoticeType.BIDDING_NOTICE] = bookmarkNotice.biddingNotice
        }

        if (!bookmarkNotice.dormitoryEntryNotice.isNullOrEmpty()) {
            nonEmptyListsWithItems[NoticeType.DORMITORY_ENTRY_NOTICE] = bookmarkNotice.dormitoryEntryNotice
        }

        if (!bookmarkNotice.scholarshipNotice.isNullOrEmpty()) {
            nonEmptyListsWithItems[NoticeType.SCHOLARSHIP_NOTICE] = bookmarkNotice.scholarshipNotice
        }

        if (!bookmarkNotice.normalNotice.isNullOrEmpty()) {
            nonEmptyListsWithItems[NoticeType.NORMAL_NOTICE] = bookmarkNotice.normalNotice
        }

        return nonEmptyListsWithItems
    }

}