package com.dev_marinov.osmdroidtest.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class CameraNavigationArgs(
    val cameraName: String?,
    val cameraType: @RawValue Any?,
    val address: String,
    val worktime: String,
    val visible: String
) : Parcelable