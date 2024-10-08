package com.neverland.domain.model.univ

data class AcademicSchedule(
    val id: Int,
    val title: String,
    val startDate: String,
    val endDate: String,
    var marked: Boolean
)
