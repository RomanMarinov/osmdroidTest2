package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.Messengers
import com.google.gson.annotations.SerializedName

data class MessengersDTO(
    @SerializedName("telegram")
    val telegram: TelegramDTO,
    @SerializedName("vk")
    val vk: VkDTO,
    @SerializedName("whatsapp")
    val whatsapp: WhatsappDTO
)  {
    fun mapToDomain() : Messengers {
        return Messengers(
            telegram = telegram.mapToDomain(),
            vk = vk.mapToDomain(),
            whatsapp = whatsapp.mapToDomain()
        )
    }
}