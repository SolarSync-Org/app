package com.solarsync.solarapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.solarsync.solarapp.ui.screens.dashboard.DashboardScreen
import com.solarsync.solarapp.ui.screens.profile.ProfileScreen

sealed class MainScreen(val route: String) {
    data object Dashboard : MainScreen("dashboard")
    data object Profile : MainScreen("profile")
}

@Composable
fun MainBottomNavigation(navController: NavController) {
    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, "Dashboard") },
            label = { Text("Dashboard") },
            selected = currentRoute == MainScreen.Dashboard.route,
            onClick = {
                navController.navigate(MainScreen.Dashboard.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, "Profile") },
            label = { Text("Profile") },
            selected = currentRoute == MainScreen.Profile.route,
            onClick = {
                navController.navigate(MainScreen.Profile.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
fun MainNavigationGraph(onSignOut: () -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { MainBottomNavigation(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = MainScreen.Dashboard.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(MainScreen.Dashboard.route) { DashboardScreen() }
            composable(MainScreen.Profile.route) {
                ProfileScreen(
                    navController = navController,
                    onSignOut = onSignOut
                )
            }
        }
    }
}
