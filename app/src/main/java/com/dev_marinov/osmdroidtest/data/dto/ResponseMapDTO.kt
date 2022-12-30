package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.ResponseMap
import com.google.gson.annotations.SerializedName

data class ResponseMapDTO(
    @SerializedName("data")
    val data: DataDTO
) {
    fun mapToDomain() : ResponseMap{
        return ResponseMap(
            data = data.mapToDomain()
        )
    }
}