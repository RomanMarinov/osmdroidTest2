package com.dev_marinov.osmdroidtest.domain.model

data class Data(
    val clientIp: String,
    val contacts: Contacts,
    val ipInfo: String,
    val locations: List<Location>,
    val mapDefault: List<MapDefault>,
    val mapMarkers: MapMarkers,
    val mapServers: List<String>,
    val promoInfo: List<PromoInfo>,
    val requirements: Requirements,
    val serverTimeInt: Int,
    val serverTimeString: String,
    val validateCallNumber: String,
    val vendors: Vendors
)