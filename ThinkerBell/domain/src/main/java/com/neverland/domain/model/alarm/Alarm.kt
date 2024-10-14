package com.neverland.domain.model.alarm

data class Alarm (
    val id: Int,
    val categoryId: Int,
    val marked: Boolean,
    val noticeTypeEnglish: String,
    val noticeTypeKorean: String,
    val pubDate: String,
    val title: String,
    val url: String,
    var viewed: Boolean
)