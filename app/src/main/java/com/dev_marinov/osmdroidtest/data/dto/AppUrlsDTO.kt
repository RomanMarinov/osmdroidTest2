package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.AppUrls
import com.google.gson.annotations.SerializedName

data class AppUrlsDTO(
    @SerializedName("appStoreUrl")
    val appStoreUrl: String,
    @SerializedName("googlePlay")
    val googlePlay: String,
    @SerializedName("googlePlayUrl")
    val googlePlayUrl: String,
    @SerializedName("ios")
    val ios: Int
) {
    fun mapToDomain() : AppUrls {
        return AppUrls(
            appStoreUrl = appStoreUrl,
            googlePlay = googlePlay,
            googlePlayUrl = googlePlayUrl,
            ios = ios
        )
    }
}