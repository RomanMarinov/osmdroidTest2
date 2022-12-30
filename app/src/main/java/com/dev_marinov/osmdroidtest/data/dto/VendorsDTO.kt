package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.Vendors
import com.google.gson.annotations.SerializedName

data class VendorsDTO(
    @SerializedName("ufanet")
    val ufanet: UfanetDTO
) {
    fun mapToDomain() : Vendors {
        return Vendors(
            ufanet = ufanet.mapToDomain()
        )
    }
}