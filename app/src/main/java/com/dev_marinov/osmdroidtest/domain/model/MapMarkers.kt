package com.dev_marinov.osmdroidtest.domain.model

data class MapMarkers(
    val cityCams: CityCams,
    val domofonCams: DomofonCams,
    val officeCams: Office,
    val outdoorCams: OutdoorCams
)