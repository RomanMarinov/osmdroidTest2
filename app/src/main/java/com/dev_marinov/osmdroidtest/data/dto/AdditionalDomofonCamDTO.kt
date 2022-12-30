package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.AdditionalDomofonCam
import com.google.gson.annotations.SerializedName

data class AdditionalDomofonCamDTO(
    @SerializedName("domofonVendor")
    val domofonVendor: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("locationTitle")
    val locationTitle: String
) {
    fun mapToDomain() : AdditionalDomofonCam {
        return AdditionalDomofonCam(
            domofonVendor = domofonVendor,
            location = location,
            locationTitle = locationTitle
        )
    }
}