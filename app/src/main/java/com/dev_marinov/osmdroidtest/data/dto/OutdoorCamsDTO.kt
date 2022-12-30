package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.OutdoorCams
import com.google.gson.annotations.SerializedName

data class OutdoorCamsDTO(
    @SerializedName("count")
    val count: Int,
    @SerializedName("markers")
    val markers: List<MarkerOutdoorCamDTO>,
    @SerializedName("title")
    val title: String
) {
    fun mapToDomain(): OutdoorCams {
        return OutdoorCams(
            count = count,
            markers = markers.map { it.mapToDomain() },
            title = title
        )
    }
}