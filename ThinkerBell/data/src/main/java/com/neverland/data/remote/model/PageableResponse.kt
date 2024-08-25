package com.neverland.data.remote.model

import com.google.gson.annotations.SerializedName

data class PageableResponse<T>(
    @SerializedName("items")
    val items: List<T>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("totalItems")
    val totalItems: Int
)
