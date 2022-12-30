package com.dev_marinov.osmdroidtest.domain.model

data class MarkerCityCam(
    val additional: AdditionalCityCam,
    val angle: Int,
    val latitude: Double,
    val longitude: Double,
    val title: String
)