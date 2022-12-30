package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.Whatsapp
import com.google.gson.annotations.SerializedName

data class WhatsappDTO(
    @SerializedName("iosId")
    val iosId: Int,
    @SerializedName("iosUrl")
    val iosUrl: String,
    @SerializedName("webUrl")
    val webUrl: String
) {
    fun mapToDomain() : Whatsapp {
        return Whatsapp(
            iosId = iosId,
            iosUrl = iosUrl,
            webUrl = webUrl
        )
    }
}