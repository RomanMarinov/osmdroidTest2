package com.dev_marinov.osmdroidtest.domain.model

data class CityCams(
    val count: Int,
    val markers: List<MarkerCityCam>,
    val title: String
)