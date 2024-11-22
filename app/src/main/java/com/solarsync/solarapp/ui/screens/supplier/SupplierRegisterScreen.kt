package com.solarsync.solarapp.ui.screens.supplier

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
import androidx.navigation.NavController
import com.solarsync.solarapp.ui.components.SolarSyncButton
import com.solarsync.solarapp.ui.components.SolarSyncTextField
import com.solarsync.solarapp.Screen
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SupplierRegisterScreen(
    navController: NavController,
    viewModel: SupplierRegisterViewModel = viewModel()
) {
    val currentStep by viewModel.currentStep
    val formData by viewModel.formData
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LinearProgressIndicator(
                progress = (currentStep + 1) / 4f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )

            when (currentStep) {
                0 -> SolutionTypeStep(
                    selectedType = formData.solutionType,
                    onTypeSelected = { type ->
                        viewModel.updateFormData(formData.copy(solutionType = type))
                        viewModel.updateStep(1)
                    }
                )
                1 -> PreferredPropertiesStep(
                    selectedType = formData.preferredProperties,
                    onNext = { type ->
                        viewModel.updateFormData(formData.copy(preferredProperties = type))
                        viewModel.updateStep(2)
                    },
                    onBack = { viewModel.updateStep(0) }
                )
                2 -> OperationAreaStep(
                    area = formData.operationArea,
                    onNext = { area ->
                        viewModel.updateFormData(formData.copy(operationArea = area))
                        viewModel.updateStep(3)
                    },
                    onBack = { viewModel.updateStep(1) }
                )
                3 -> SupplierBasicInfoStep(
                    formData = formData,
                    onNext = { name, email, password, cnpj ->
                        viewModel.updateFormData(formData.copy(
                            name = name,
                            email = email,
                            password = password,
                            cnpj = cnpj
                        ))
                        viewModel.registerSupplier(
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
fun SolutionTypeStep(
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
            text = "Qual tipo de solução você oferece?",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        val options = listOf(
            "Aluguel de placas solares",
            "Venda de sistemas completos",
            "Investimento em projetos específicos"
        )
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
fun PreferredPropertiesStep(
    selectedType: String,
    onNext: (String) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Quais imóveis você prefere atender?",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        val options = listOf("Residências", "Galpões", "Indústrias")
        options.forEach { option ->
            SolarSyncButton(
                text = option,
                onClick = { onNext(option) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        SolarSyncButton(
            text = "Voltar",
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun OperationAreaStep(
    area: String,
    onNext: (String) -> Unit,
    onBack: () -> Unit
) {
    var currentArea by remember { mutableStateOf(area) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Área de Atuação",
            style = MaterialTheme.typography.headlineSmall
        )

        SolarSyncTextField(
            value = currentArea,
            onValueChange = { currentArea = it },
            label = "Cidade de atuação"
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
                onClick = { onNext(currentArea) },
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun SupplierBasicInfoStep(
    formData: SupplierFormData,
    onNext: (String, String, String, String) -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf(formData.name) }
    var email by remember { mutableStateOf(formData.email) }
    var password by remember { mutableStateOf(formData.password) }
    var cnpj by remember { mutableStateOf(formData.cnpj) }

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
            label = "Nome da empresa"
        )

        SolarSyncTextField(
            value = email,
            onValueChange = { email = it },
            label = "E-mail"
        )

        SolarSyncTextField(
            value = password,
            onValueChange = { password = it },
            label = "Senha",
            visualTransformation = PasswordVisualTransformation()
        )

        SolarSyncTextField(
            value = cnpj,
            onValueChange = { cnpj = it },
            label = "CNPJ"
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
                onClick = { onNext(name, email, password, cnpj) },
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )
        }
    }
}