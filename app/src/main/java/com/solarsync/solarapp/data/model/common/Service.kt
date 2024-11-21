package com.solarsync.solarapp.data.model.common

data class Service(
    val type: ServiceType = ServiceType.SALE,
    val description: String = "",
    val minPrice: Double = 0.0,
    val maxPrice: Double = 0.0
)

enum class ServiceType {
    SALE,
    RENTAL,
    MAINTENANCE
}