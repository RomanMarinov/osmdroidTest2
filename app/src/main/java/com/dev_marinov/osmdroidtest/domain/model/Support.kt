package com.dev_marinov.osmdroidtest.domain.model

data class Support(
    val email: String,
    val messengers: Messengers,
    val phone: Phone,
    val worktime: List<String>
)