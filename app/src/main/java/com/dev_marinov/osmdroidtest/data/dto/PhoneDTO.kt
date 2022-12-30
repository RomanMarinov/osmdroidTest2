package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.Phone
import com.google.gson.annotations.SerializedName

data class PhoneDTO(
    @SerializedName("dialer")
    val dialer: String,
    @SerializedName("visible")
    val visible: String
) {
    fun mapToDomain() : Phone {
        return Phone(
            dialer = dialer,
            visible = visible
        )
    }
}