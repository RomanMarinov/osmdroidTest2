package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.Location
import com.google.gson.annotations.SerializedName

data class LocationDTO(
    @SerializedName("camerasAvailable")
    val camerasAvailable: Boolean,
    @SerializedName("center")
    val center: String,
    @SerializedName("leftTopLat")
    val leftTopLat: String,
    @SerializedName("leftTopLon")
    val leftTopLon: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("rightBottomLat")
    val rightBottomLat: String,
    @SerializedName("rightBottomLon")
    val rightBottomLon: String,
    @SerializedName("title")
    val title: String
) {
    fun mapToDomain() : Location{
        return Location(
            camerasAvailable = camerasAvailable,
            center = center,
            leftTopLat = leftTopLat,
            leftTopLon = leftTopLon,
            location = location,
            rightBottomLat = rightBottomLat,
            rightBottomLon = rightBottomLon,
            title = title
        )
    }
}