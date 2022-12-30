package com.dev_marinov.osmdroidtest.domain.model

data class MarkerOffice(
    val additional: AdditionalOfficeCam,
    val angle: Int,
    val latitude: Double,
    val longitude: Double,
    val title: String
)