package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.Vk
import com.google.gson.annotations.SerializedName

data class VkDTO(
    @SerializedName("iosId")
    val iosId: Int,
    @SerializedName("iosUrl")
    val iosUrl: String,
    @SerializedName("webUrl")
    val webUrl: String
) {
     fun mapToDomain() : Vk{
         return Vk(
             iosId = iosId,
             iosUrl = iosUrl,
             webUrl = webUrl
         )
     }
}