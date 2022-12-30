package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.MapMarkers
import com.google.gson.annotations.SerializedName

data class MapMarkersDTO(
    @SerializedName("cityCams")
    val cityCams: CityCamsDTO,
    @SerializedName("domofonCams")
    val domofonCams: DomofonCamsDTO,
    @SerializedName("officeCams")
    val officeCams: OfficeDTO,
    @SerializedName("outdoorCams")
    val outdoorCams: OutdoorCamsDTO
) {
    fun mapToDomain() : MapMarkers {
        return MapMarkers(
            cityCams = cityCams.mapToDomain(),
            domofonCams = domofonCams.mapToDomain(),
            officeCams = officeCams.mapToDomain(),
            outdoorCams = outdoorCams.mapToDomain()
        )
    }
}