package com.solarsync.solarapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.solarsync.solarapp.ui.screens.auth.LoginScreen
import com.solarsync.solarapp.ui.screens.client.ClientRegisterScreen
import com.solarsync.solarapp.ui.screens.profile.ProfileScreen
import com.solarsync.solarapp.ui.screens.supplier.SupplierRegisterScreen
import com.solarsync.solarapp.ui.screens.welcome.WelcomeScreen
import com.solarsync.solarapp.ui.theme.SolarAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SolarAppTheme {
                NavHost(navController = navController, startDestination = Screen.Welcome.route) {
                    composable(Screen.Welcome.route) { WelcomeScreen(navController) }
                    composable(Screen.ClientRegister.route) { ClientRegisterScreen(navController) }
                    composable(Screen.SupplierRegister.route) { SupplierRegisterScreen(navController) }
                    composable(Screen.Login.route) { LoginScreen(navController) }
                    //composable(Screen.Dashboard.route) { DashboardScreen() }
                    composable(Screen.Profile.route) { ProfileScreen() }
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun RegistroPreview() {
//    SolarAppTheme {
//        ClientMultiStepForm()
//    }
//}