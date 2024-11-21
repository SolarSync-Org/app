package com.solarsync.solarapp.ui.screens.auth

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ClientRegistrationScreen(
    onRegistrationComplete: () -> Unit
) {
    var personalInfo by remember { mutableStateOf(PersonalInfo()) }
    var installationInfo by remember { mutableStateOf(InstallationInfo()) }
    var contactInfo by remember { mutableStateOf(ContactInfo()) }

    val steps = listOf(
        // Step 1: Installation Type
        @Composable {
            InstallationTypeStep(
                installationInfo = installationInfo,
                onInfoChange = { installationInfo = it }
            )
        },

        // Step 2: Energy Consumption
        @Composable {
            EnergyConsumptionStep(
                installationInfo = installationInfo,
                onInfoChange = { installationInfo = it }
            )
        },

        // Step 3: Personal Information
        @Composable {
            PersonalInfoStep(
                personalInfo = personalInfo,
                onInfoChange = { personalInfo = it }
            )
        }
    )

    MultiStepForm(
        steps = steps,
        onComplete = {
            // Handle registration completion
            onRegistrationComplete()
        }
    )
}

@Composable
fun InstallationTypeStep(
    installationInfo: InstallationInfo,
    onInfoChange: (InstallationInfo) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "What type of installation are you looking for?",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Installation type options
        listOf("Residential", "Industrial", "Commercial").forEach { type ->
            Button(
                onClick = {
                    onInfoChange(installationInfo.copy(type = type))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(type)
            }
        }
    }
}

// Add data classes for form state
data class PersonalInfo(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val cpf: String = ""
)

data class InstallationInfo(
    val type: String = "",
    val monthlyConsumption: String = "",
    val address: String = ""
)

data class ContactInfo(
    val phone: String = "",
    val preferredContact: String = ""
)