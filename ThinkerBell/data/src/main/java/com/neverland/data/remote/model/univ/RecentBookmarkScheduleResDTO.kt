package com.neverland.data.remote.model.univ

import com.google.gson.annotations.SerializedName

data class RecentBookmarkScheduleResDTO(
    @SerializedName("endDate") val endDate: String,
    @SerializedName("id") val id: Int,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("title") val title: String
)
