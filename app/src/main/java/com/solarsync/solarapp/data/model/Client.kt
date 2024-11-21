package com.solarsync.solarapp.data.model

import com.solarsync.solarapp.data.model.common.Address
import com.solarsync.solarapp.data.model.common.ContactInfo

data class Client(
    val userId: String = "",
    val name: String = "",
    val cpf: String = "",
    val installationType: String = "",
    val monthlyConsumption: Double = 0.0,
    val address: Address = Address(),
    val contactInfo: ContactInfo = ContactInfo()
)