package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.Requirements
import com.google.gson.annotations.SerializedName

data class RequirementsDTO(
    @SerializedName("apiVersion")
    val apiVersion: Int,
    @SerializedName("appVersion")
    val appVersion: Int
) {
    fun mapToDomain(): Requirements {
        return Requirements(
            apiVersion = apiVersion,
            appVersion = appVersion
        )
    }
}