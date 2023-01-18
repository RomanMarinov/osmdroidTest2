package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.AdditionalCityCam
import com.google.gson.annotations.SerializedName

data class AdditionalCityCamDTO(
    @SerializedName("albumId")
    val albumId: Int,
    @SerializedName("cameraName")
    val cameraName: String,
    @SerializedName("dtpCounts")
    val dtpCounts: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("locationTitle")
    val locationTitle: String,
    @SerializedName("previewUrl")
    val previewUrl: String,
    @SerializedName("server")
    val server: String,
    @SerializedName("token")
    val token: String
) {
    fun mapToDomain() : AdditionalCityCam {
        return AdditionalCityCam(
            albumId = albumId,
            cameraName = cameraName,
            dtpCounts = dtpCounts,
            location = location,
            locationTitle = location,
            server = server,
            token = token,
            previewUrl = previewUrl
        )
    }
}