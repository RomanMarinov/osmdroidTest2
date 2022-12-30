package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.MarkerCityCam
import com.google.gson.annotations.SerializedName

data class MarkerCityCamDTO(
    @SerializedName("additional")
    val additional: AdditionalCityCamDTO,
    @SerializedName("angle")
    val angle: Int,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("title")
    val title: String
) {
    fun mapToDomain() : MarkerCityCam {
        return MarkerCityCam(
            additional = additional.mapToDomain(),
            angle = angle,
            latitude = latitude,
            longitude = longitude,
            title = title
        )
    }
}