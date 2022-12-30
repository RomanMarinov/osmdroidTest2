package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.AdditionalOfficeCam
import com.google.gson.annotations.SerializedName

data class AdditionalOfficeDTO(
    @SerializedName("address")
    val address: String,
    @SerializedName("phone")
    val phone: PhoneDTO,
    @SerializedName("worktime")
    val worktime: String
) {
    fun mapToDomain() : AdditionalOfficeCam {
        return AdditionalOfficeCam(
            address = address,
            phone = phone.mapToDomain(),
            worktime = worktime
        )
    }
}