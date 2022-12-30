package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.Telegram
import com.google.gson.annotations.SerializedName

data class TelegramDTO(
    @SerializedName("iosId")
    val iosId: Int,
    @SerializedName("iosUrl")
    val iosUrl: String,
    @SerializedName("webUrl")
    val webUrl: String
) {
    fun mapToDomain() : Telegram {
        return Telegram(
            iosId = iosId,
            iosUrl = iosUrl,
            webUrl = webUrl
        )
    }
}