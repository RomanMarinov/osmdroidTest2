package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.Office
import com.google.gson.annotations.SerializedName

data class OfficeDTO(
    @SerializedName("count")
    val count: Int,
    @SerializedName("markers")
    val markers: List<MarkerOfficeDTO>,
    @SerializedName("title")
    val title: String
) {
    fun mapToDomain() : Office {
        return Office(
            count = count,
            markers = markers.map { it.mapToDomain() },
            title = title
        )
    }
}