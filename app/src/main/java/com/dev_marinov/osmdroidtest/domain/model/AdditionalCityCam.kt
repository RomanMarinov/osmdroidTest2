package com.dev_marinov.osmdroidtest.domain.model

data class AdditionalCityCam(
    val albumId: Int,
    val cameraName: String,
    val dtpCounts: Int,
    val location: String,
    val locationTitle: String,
    val server: String,
    val token: String
)