package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.Support
import com.google.gson.annotations.SerializedName

data class SupportDTO(
    @SerializedName("email")
    val email: String,
    @SerializedName("messengers")
    val messengers: MessengersDTO,
    @SerializedName("phone")
    val phone: PhoneDTO,
    @SerializedName("worktime")
    val worktime: List<String>
) {
    fun mapToDomain() : Support {
        return Support(
            email = email,
            messengers = messengers.mapToDomain(),
            phone = phone.mapToDomain(),
            worktime = worktime
        )
    }
}