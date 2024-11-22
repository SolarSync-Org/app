package com.solarsync.solarapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.solarsync.solarapp.ui.screens.auth.LoginScreen
import com.solarsync.solarapp.ui.screens.client.ClientRegisterScreen
import com.solarsync.solarapp.ui.screens.supplier.SupplierRegisterScreen
import com.solarsync.solarapp.ui.screens.welcome.WelcomeScreen
import com.solarsync.solarapp.ui.theme.SolarAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isLoggedIn = remember { mutableStateOf(isUserLoggedIn()) }

            SolarAppTheme {
                if (isLoggedIn.value) {
                    MainNavigationGraph(
                        onSignOut = {
                            isLoggedIn.value = false
                        }
                    )
                } else {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Welcome.route
                    ) {
                        composable(Screen.Welcome.route) { WelcomeScreen(navController) }
                        composable(Screen.ClientRegister.route) { ClientRegisterScreen(navController) }
                        composable(Screen.SupplierRegister.route) { SupplierRegisterScreen(navController) }
                        composable(Screen.Login.route) {
                            LoginScreen(
                                navController = navController,
                                onLoginSuccess = {
                                    isLoggedIn.value = true
                                }
                            )}
                    }
                }
            }
        }
    }
}

private fun isUserLoggedIn(): Boolean {
    val currentUser = FirebaseAuth.getInstance().currentUser
    return currentUser != null
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