package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.Data
import com.google.gson.annotations.SerializedName

data class DataDTO(
    @SerializedName("clientIp")
    val clientIp: String,
    @SerializedName("contacts")
    val contacts: ContactsDTO,
    @SerializedName("ipInfo")
    val ipInfo: String,
    @SerializedName("locations")
    val locations: List<LocationDTO>,
    @SerializedName("mapDefault")
    val mapDefault: List<MapDefaultDTO>,
    @SerializedName("mapMarkers")
    val mapMarkers: MapMarkersDTO,
    @SerializedName("mapServers")
    val mapServers: List<String>,
    @SerializedName("promoInfo")
    val promoInfo: List<PromoInfoDTO>,
    @SerializedName("requirements")
    val requirements: RequirementsDTO,
    @SerializedName("serverTimeInt")
    val serverTimeInt: Int,
    @SerializedName("serverTimeString")
    val serverTimeString: String,
    @SerializedName("validateCallNumber")
    val validateCallNumber: String,
    @SerializedName("vendors")
    val vendors: VendorsDTO
) {
    fun mapToDomain() : Data {
        return Data(
            clientIp = clientIp,
            contacts = contacts.mapToDomain(),
            ipInfo = ipInfo,
            locations = locations.map { it.mapToDomain() },
            mapDefault = mapDefault.map { it.mapToDomain() },
            mapMarkers = mapMarkers.mapToDomain(),
            mapServers = mapServers,
            promoInfo = promoInfo.map { it.mapToDomain() },
            requirements = requirements.mapToDomain(),
            serverTimeInt = serverTimeInt,
            serverTimeString = serverTimeString,
            validateCallNumber = validateCallNumber,
            vendors = vendors.mapToDomain()
        )
    }
}