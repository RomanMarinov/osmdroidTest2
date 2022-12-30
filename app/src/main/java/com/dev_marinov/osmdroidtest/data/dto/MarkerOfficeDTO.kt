package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.MarkerOffice
import com.google.gson.annotations.SerializedName

data class MarkerOfficeDTO(
    @SerializedName("additional")
    val additional: AdditionalOfficeDTO,
    @SerializedName("angle")
    val angle: Int,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("title")
    val title: String
) {
    fun mapToDomain() : MarkerOffice{
        return MarkerOffice(
            additional = additional.mapToDomain(),
            angle = angle,
            latitude = latitude,
            longitude = longitude,
            title = title
        )
    }
}