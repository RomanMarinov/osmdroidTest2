package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.CityCams
import com.google.gson.annotations.SerializedName

data class CityCamsDTO(
    @SerializedName("count")
    val count: Int,
    @SerializedName("markers")
    val markers: List<MarkerCityCamDTO>,
    @SerializedName("title")
    val title: String
) {
    fun mapToDomain() : CityCams {
        return CityCams(
            count = count,
            markers = markers.map { it.mapToDomain() },
            title = title
        )
    }
}