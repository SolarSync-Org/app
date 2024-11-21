package com.solarsync.solarapp.ui.screens.auth

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SupplierRegistrationScreen(
    viewModel: SupplierRegistrationViewModel = hiltViewModel(),
    onRegistrationComplete: () -> Unit
) {
    var companyInfo by remember { mutableStateOf(CompanyInfo()) }
    var servicesInfo by remember { mutableStateOf(ServicesInfo()) }
    var coverageInfo by remember { mutableStateOf(CoverageInfo()) }
    var contactInfo by remember { mutableStateOf(ContactInfo()) }

    val steps = listOf(
        // Step 1: Company Information
        @Composable {
            CompanyInfoStep(
                companyInfo = companyInfo,
                onInfoChange = { companyInfo = it }
            )
        },

        // Step 2: Services Offered
        @Composable {
            ServicesStep(
                servicesInfo = servicesInfo,
                onInfoChange = { servicesInfo = it }
            )
        },

        // Step 3: Coverage Area
        @Composable {
            CoverageAreaStep(
                coverageInfo = coverageInfo,
                onInfoChange = { coverageInfo = it }
            )
        },

        // Step 4: Contact and Final Details
        @Composable {
            FinalDetailsStep(
                contactInfo = contactInfo,
                onInfoChange = { contactInfo = it }
            )
        }
    )

    MultiStepForm(
        steps = steps,
        onComplete = {
            viewModel.registerSupplier(
                companyInfo = companyInfo,
                servicesInfo = servicesInfo,
                coverageInfo = coverageInfo,
                contactInfo = contactInfo,
                onSuccess = onRegistrationComplete
            )
        }
    )
}

@Composable
fun CompanyInfoStep(
    companyInfo: CompanyInfo,
    onInfoChange: (CompanyInfo) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Company Information",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = companyInfo.name,
            onValueChange = {
                onInfoChange(companyInfo.copy(name = it))
            },
            label = { Text("Company Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = companyInfo.cnpj,
            onValueChange = {
                onInfoChange(companyInfo.copy(cnpj = it))
            },
            label = { Text("CNPJ") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Data classes for form state
data class CompanyInfo(
    val name: String = "",
    val cnpj: String = "",
    val description: String = ""
)

data class ServicesInfo(
    val services: List<Service> = emptyList()
)

data class CoverageInfo(
    val cities: List<String> = emptyList(),
    val states: List<String> = emptyList()
)