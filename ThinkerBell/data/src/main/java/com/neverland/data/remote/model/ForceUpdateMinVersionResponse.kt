package com.neverland.data.remote.model

import com.google.gson.annotations.SerializedName

data class ForceUpdateMinVersionResponse(
    @SerializedName("versionCode")
    val versionCode: String,
    @SerializedName("versionName")
    val versionName: String
)
