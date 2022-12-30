package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.MarkerDomofonCam
import com.google.gson.annotations.SerializedName

data class MarkerDomofonCamDTO(
    @SerializedName("additional")
    val additional: AdditionalDomofonCamDTO,
    @SerializedName("angle")
    val angle: Int,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("title")
    val title: String
) {
    fun mapToDomain() : MarkerDomofonCam{
        return MarkerDomofonCam(
            additional = additional.mapToDomain(),
            angle = angle,
            latitude = latitude,
            longitude = longitude,
            title = title
        )
    }
}