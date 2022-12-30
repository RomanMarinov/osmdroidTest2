package com.dev_marinov.osmdroidtest.domain.model

data class OutdoorCams(
    val count: Int,
    val markers: List<MarkerOutdoorCam>,
    val title: String
)