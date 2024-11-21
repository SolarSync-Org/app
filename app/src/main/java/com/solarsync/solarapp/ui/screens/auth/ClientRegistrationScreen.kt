package com.solarsync.solarapp.ui.screens.auth

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solarsync.solarapp.ui.components.MultiStepForm
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

fun registerUser(
    email: String,
    password: String,
    additionalData: Map<String, Any>, // Dados como CPF, tipo de instalação, etc.
    collection: String, // "clients" ou "suppliers"
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Obtem o UID gerado pelo Firebase Auth
                val userId = task.result?.user?.uid ?: return@addOnCompleteListener
                val data = additionalData + mapOf("uid" to userId)

                // Salva os dados no Firestore
                FirebaseFirestore.getInstance()
                    .collection(collection)
                    .document(userId)
                    .set(data)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e -> onError(e.message ?: "Erro ao salvar dados no Firestore") }
            } else {
                onError(task.exception?.message ?: "Erro ao criar conta")
            }
        }
}


@Composable
fun ClientMultiStepForm() {
    var currentStep by remember { mutableStateOf(1) }

    when (currentStep) {
        1 -> ClientStep1(onNext = { currentStep++ }, selectedType = { /* Salvar tipo */ })
        2 -> ClientStep2(onNext = { currentStep++ }, onInput = { energyBill, consumptionProfile -> /* Salvar consumo */ })
        3 -> ClientStep3(onNext = { currentStep++ }, onInput = { city, address -> /* Salvar localização */ })
        4 -> ClientStep4(onFinish = { /* Salvar dados e enviar para API */ }, onInput = { name, email, password, cpf -> /* Salvar dados básicos */ })
    }

    LinearProgressIndicator(
        progress = { currentStep / 4f },
    )
}

@Composable
fun ClientStep1(onNext: () -> Unit, selectedType: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Selecione o tipo de instalação", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { selectedType("Residência"); onNext() }) {
            Text("Residência")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { selectedType("Galpão"); onNext() }) {
            Text("Galpão")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { selectedType("Indústria"); onNext() }) {
            Text("Indústria")
        }
    }
}

@Composable
fun ClientStep2(onNext: () -> Unit, onInput: (Double, String) -> Unit) {
    var energyBill by remember { mutableStateOf("") }
    var consumptionProfile by remember { mutableStateOf("Médio") }
    val options = listOf("Baixo", "Médio", "Alto")

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Informe os dados de consumo de energia", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = energyBill,
            onValueChange = { energyBill = it },
            label = { Text("Valor médio da conta (R$)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownMenu(options = options, selectedOption = consumptionProfile, onOptionSelected = { consumptionProfile = it })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onInput(energyBill.toDouble(), consumptionProfile); onNext() }) {
            Text("Próximo")
        }
    }
}

@Composable
fun DropdownMenu(options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedOption)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = { onOptionSelected(option); expanded = false }, text = { Text(option) })
            }
        }
    }
}

@Composable
fun ClientStep3(onNext: () -> Unit, onInput: (String, String) -> Unit) {
    var city by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Informe a localização do imóvel", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Cidade") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Endereço") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onInput(city, address); onNext() }) {
            Text("Próximo")
        }
    }
}

@Composable
fun ClientStep4(onFinish: () -> Unit, onInput: (String, String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Dados do Cliente", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = cpf,
            onValueChange = { cpf = it },
            label = { Text("CPF") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            registerUser(
                email = email,
                password = password,
                additionalData = mapOf("name" to name, "cpf" to cpf),
                collection = "clients",
                onSuccess = onFinish,
                onError = { error -> /* Exibir mensagem de erro */ }
            )
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Finalizar Cadastro")
        }
    }
}