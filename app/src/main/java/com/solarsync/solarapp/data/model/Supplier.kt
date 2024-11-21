package com.solarsync.solarapp.data.model

import com.solarsync.solarapp.data.model.common.Address
import com.solarsync.solarapp.data.model.common.ContactInfo
import com.solarsync.solarapp.data.model.common.Service

data class Supplier(
    val userId: String = "",
    val companyName: String = "",
    val cnpj: String = "",
    val services: List<Service> = emptyList(),
    val coverageAreas: List<String> = emptyList(),
    val address: Address = Address(),
    val contactInfo: ContactInfo = ContactInfo()
)