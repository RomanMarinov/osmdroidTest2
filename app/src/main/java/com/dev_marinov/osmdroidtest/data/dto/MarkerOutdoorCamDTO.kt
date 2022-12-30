package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.MarkerOutdoorCam
import com.google.gson.annotations.SerializedName

data class MarkerOutdoorCamDTO(
    @SerializedName("additional")
    val additional: AdditionalOutdoorCamDTO,
    @SerializedName("angle")
    val angle: Int,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("title")
    val title: String
) {
    fun mapToDomain() : MarkerOutdoorCam {
        return MarkerOutdoorCam(
            additional = additional.mapToDomain(),
            angle = angle,
            latitude = latitude,
            longitude = longitude,
            title = title
        )
    }
}