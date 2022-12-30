package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.DomofonCams
import com.google.gson.annotations.SerializedName

data class DomofonCamsDTO(
    @SerializedName("count")
    val count: Int,
    @SerializedName("markers")
    val markers: List<MarkerDomofonCamDTO>,
    @SerializedName("title")
    val title: String
) {
    fun mapToDomain() : DomofonCams {
        return DomofonCams(
            count = count,
            markers = markers.map { it.mapToDomain() },
            title = title
        )
    }
}