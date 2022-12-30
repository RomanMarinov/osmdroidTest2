package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.Ufanet
import com.google.gson.annotations.SerializedName

data class UfanetDTO(
    @SerializedName("appUrls")
    val appUrls: AppUrlsDTO
) {
    fun mapToDomain(): Ufanet {
        return Ufanet(
            appUrls = appUrls.mapToDomain()
        )
    }
}