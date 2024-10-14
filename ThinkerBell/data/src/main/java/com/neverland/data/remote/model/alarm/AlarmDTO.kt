package com.neverland.data.remote.model.alarm

import com.google.gson.annotations.SerializedName

data class AlarmDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("marked")
    val marked: Boolean,
    @SerializedName("noticeTypeEnglish")
    val noticeTypeEnglish: String,
    @SerializedName("noticeTypeKorean")
    val noticeTypeKorean: String,
    @SerializedName("pubDate")
    val pubDate: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String?,
    @SerializedName("viewed")
    val viewed: Boolean
)