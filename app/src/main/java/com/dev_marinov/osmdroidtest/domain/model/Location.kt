package com.dev_marinov.osmdroidtest.domain.model

data class Location(
    val camerasAvailable: Boolean,
    val center: String,
    val leftTopLat: String,
    val leftTopLon: String,
    val location: String,
    val rightBottomLat: String,
    val rightBottomLon: String,
    val title: String
)