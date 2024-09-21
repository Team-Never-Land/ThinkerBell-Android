package com.neverland.domain.model.univ

data class BookmarkScheduleGroup(
    val year: Int,  // 연도 추가
    val month: String,
    val schedules: List<AcademicSchedule>
)

fun groupSchedulesByYearAndMonth(schedules: List<AcademicSchedule>): List<BookmarkScheduleGroup> {
    return schedules.groupBy { schedule ->
        // 날짜에서 연도와 월 추출 (예: "2024-09-10" -> "2024년", "9월")
        val dateParts = schedule.startDate.split("-")
        val year = dateParts[0].toInt()
        val month = dateParts[1]
        Pair(year, month)
    }.map { (yearMonthPair, schedules) ->
        BookmarkScheduleGroup(yearMonthPair.first, yearMonthPair.second, schedules)
    }
}
