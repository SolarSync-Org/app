package com.solarsync.solarapp

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object ClientRegister : Screen("client_register")
    data object SupplierRegister : Screen("supplier_register")
    data object Login : Screen("login")
    data object Dashboard : Screen("dashboard")
    data object Profile : Screen("profile")
}