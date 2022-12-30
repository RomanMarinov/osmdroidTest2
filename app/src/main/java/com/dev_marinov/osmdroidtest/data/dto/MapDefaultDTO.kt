package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.MapDefault
import com.google.gson.annotations.SerializedName

data class MapDefaultDTO(
    @SerializedName("cityName")
    val cityName: String,
    @SerializedName("gpsLat")
    val gpsLat: Double,
    @SerializedName("gpsLon")
    val gpsLon: Double
) {
    fun mapToDomain() : MapDefault {
        return MapDefault(
            cityName = cityName,
            gpsLat = gpsLat,
            gpsLon = gpsLon
        )
    }
}