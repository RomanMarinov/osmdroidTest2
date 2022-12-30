package com.dev_marinov.osmdroidtest.domain.model

data class MarkerOutdoorCam(
    val additional: AdditionalOutdoorCam,
    val angle: Int,
    val latitude: Double,
    val longitude: Double,
    val title: String
)