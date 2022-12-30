package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.PromoInfo
import com.google.gson.annotations.SerializedName

data class PromoInfoDTO(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
) {
    fun mapToDomain() : PromoInfo {
        return PromoInfo(
            description = description,
            icon = icon,
            title = title,
            url = url
        )
    }
}