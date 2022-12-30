package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.AdditionalOfficeCam
import com.dev_marinov.osmdroidtest.domain.model.AdditionalOutdoorCam
import com.google.gson.annotations.SerializedName

data class AdditionalOutdoorCamDTO(
    @SerializedName("cameraName")
    val cameraName: String
) {
    fun mapToDomain() : AdditionalOutdoorCam {
        return AdditionalOutdoorCam(
            cameraName = cameraName
        )
    }
}