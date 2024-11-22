package com.solarsync.solarapp.ui.screens.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.solarsync.solarapp.Screen
import com.solarsync.solarapp.ui.components.SolarSyncButton

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SolarSyncButton(
            text = "Login",
            onClick = { navController.navigate(Screen.Login.route) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SolarSyncButton(
            text = "Registrar cliente",
            onClick = { navController.navigate(Screen.ClientRegister.route) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SolarSyncButton(
            text = "Registrar fornecedor",
            onClick = { navController.navigate(Screen.SupplierRegister.route) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    val navController = rememberNavController()
    WelcomeScreen(navController = navController)
}