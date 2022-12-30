package com.dev_marinov.osmdroidtest.data.dto

import com.dev_marinov.osmdroidtest.domain.model.Contacts
import com.google.gson.annotations.SerializedName

data class ContactsDTO(
    @SerializedName("support")
    val support: SupportDTO
) {
    fun mapToDomain() : Contacts {
        return Contacts(
            support = support.mapToDomain()
        )
    }
}