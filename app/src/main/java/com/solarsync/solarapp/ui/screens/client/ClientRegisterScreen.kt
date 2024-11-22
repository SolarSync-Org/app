package com.solarsync.solarapp.ui.screens.client

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.solarsync.solarapp.Screen
import com.solarsync.solarapp.ui.components.SolarSyncButton
import com.solarsync.solarapp.ui.components.SolarSyncTextField

@Composable
fun ClientRegisterScreen(
    navController: NavController,
    viewModel: ClientRegisterViewModel = viewModel()
) {
    val currentStep by viewModel.currentStep
    val formData by viewModel.formData
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Progress Indicator
            LinearProgressIndicator(
                progress = (currentStep + 1) / 4f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )

            when (currentStep) {
                0 -> InstallationTypeStep(
                    selectedType = formData.installationType,
                    onTypeSelected = { type ->
                        viewModel.updateFormData(formData.copy(installationType = type))
                        viewModel.updateStep(1)
                    }
                )
                1 -> EnergyConsumptionStep(
                    consumption = formData.energyConsumption,
                    monthlyBill = formData.monthlyBill,
                    onNext = { consumption, bill ->
                        viewModel.updateFormData(formData.copy(
                            energyConsumption = consumption,
                            monthlyBill = bill
                        ))
                        viewModel.updateStep(2)
                    },
                    onBack = { viewModel.updateStep(0) }
                )
                2 -> LocationStep(
                    city = formData.city,
                    address = formData.address,
                    onNext = { city, address ->
                        viewModel.updateFormData(formData.copy(
                            city = city,
                            address = address
                        ))
                        viewModel.updateStep(3)
                    },
                    onBack = { viewModel.updateStep(1) }
                )
                3 -> BasicInfoStep(
                    formData = formData,
                    onNext = { name, email, password, cpf ->
                        viewModel.updateFormData(formData.copy(
                            name = name,
                            email = email,
                            password = password,
                            cpf = cpf
                        ))
                        viewModel.registerClient(
                            onSuccess = {
                                Toast.makeText(context, "Registro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                                navController.navigate(Screen.Login.route)
                            },
                            onError = { error ->
                                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    onBack = { viewModel.updateStep(2) }
                )
            }
        }
    }
}

@Composable
fun InstallationTypeStep(
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Qual tipo de instalação você deseja?",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        val options = listOf("Residência", "Galpão", "Indústria")
        options.forEach { option ->
            SolarSyncButton(
                text = option,
                onClick = { onTypeSelected(option) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun EnergyConsumptionStep(
    consumption: String,
    monthlyBill: String,
    onNext: (String, String) -> Unit,
    onBack: () -> Unit
) {
    var currentConsumption by remember { mutableStateOf(consumption) }
    var currentBill by remember { mutableStateOf(monthlyBill) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Dados de Consumo de Energia",
            style = MaterialTheme.typography.headlineSmall
        )

        SolarSyncTextField(
            value = currentConsumption,
            onValueChange = { currentConsumption = it },
            label = "Consumo mensal (kWh)"
        )

        SolarSyncTextField(
            value = currentBill,
            onValueChange = { currentBill = it },
            label = "Valor da conta mensal (R$)"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SolarSyncButton(
                text = "Voltar",
                onClick = onBack,
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            SolarSyncButton(
                text = "Próximo",
                onClick = { onNext(currentConsumption, currentBill) },
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun LocationStep(
    city: String,
    address: String,
    onNext: (String, String) -> Unit,
    onBack: () -> Unit
) {
    var currentCity by remember { mutableStateOf(city) }
    var currentAddress by remember { mutableStateOf(address) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Localização do Imóvel",
            style = MaterialTheme.typography.headlineSmall
        )

        SolarSyncTextField(
            value = currentCity,
            onValueChange = { currentCity = it },
            label = "Cidade"
        )

        SolarSyncTextField(
            value = currentAddress,
            onValueChange = { currentAddress = it },
            label = "Endereço completo"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SolarSyncButton(
                text = "Voltar",
                onClick = onBack,
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            SolarSyncButton(
                text = "Próximo",
                onClick = { onNext(currentCity, currentAddress) },
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun BasicInfoStep(
    formData: ClientFormData,
    onNext: (String, String, String, String) -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf(formData.name) }
    var email by remember { mutableStateOf(formData.email) }
    var password by remember { mutableStateOf(formData.password) }
    var cpf by remember { mutableStateOf(formData.cpf) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Informações Básicas",
            style = MaterialTheme.typography.headlineSmall
        )

        SolarSyncTextField(
            value = name,
            onValueChange = { name = it },
            label = "Nome completo"
        )

        SolarSyncTextField(
            value = email,
            onValueChange = { email = it },
            label = "E-mail"
        )

        SolarSyncTextField(
            value = password,
            isPassword = true,
            onValueChange = { password = it },
            label = "Senha",
            visualTransformation = PasswordVisualTransformation()
        )

        SolarSyncTextField(
            value = cpf,
            onValueChange = { cpf = it },
            label = "CPF"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SolarSyncButton(
                text = "Voltar",
                onClick = onBack,
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            SolarSyncButton(
                text = "Finalizar",
                onClick = { onNext(name, email, password, cpf) },
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )
        }
    }
}
