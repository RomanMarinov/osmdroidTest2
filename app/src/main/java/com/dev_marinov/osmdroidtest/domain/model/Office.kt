package com.dev_marinov.osmdroidtest.domain.model

data class Office(
    val count: Int,
    val markers: List<MarkerOffice>,
    val title: String
)