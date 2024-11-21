package com.solarsync.solarapp.data.model

data class User(
    val id: String = "",
    val email: String = "",
    val type: UserType = UserType.CLIENT
)

enum class UserType {
    CLIENT,
    SUPPLIER
}