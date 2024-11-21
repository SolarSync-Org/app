package com.solarsync.solarapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.solarsync.solarapp.ui.theme.SolarAppTheme
import com.solarsync.solarapp.ui.screens.auth.ClientMultiStepForm
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.solarsync.solarapp.ui.components.MultiStepForm

class MainActivity : ComponentActivity() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            SolarAppTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "clientRegistration") {
//                    composable("home") {
//                        HomeScreen()
//                    }
                    composable("clientRegistration") {
                        ClientMultiStepForm()
                    }
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

@Preview(showBackground = true)
@Composable
fun RegistroPreview() {
    SolarAppTheme {
        ClientMultiStepForm()
    }
}