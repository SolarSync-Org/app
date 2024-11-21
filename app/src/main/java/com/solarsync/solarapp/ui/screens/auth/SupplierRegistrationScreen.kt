package com.solarsync.solarapp.ui.screens.auth

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun SupplierRegistration() {
    var currentStep by remember { mutableStateOf(0) }
    var city by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }

    val onNext = {
        currentStep += 1
    }

    val onFinish = {
        // Aqui você irá salvar os dados do fornecedor e enviar para o Firebase.
        // Agora é hora de integrar o Firebase.
    }

    when (currentStep) {
        0 -> SupplierStep1(onNext)
        1 -> SupplierStep2(onNext)
        2 -> SupplierStep3(city, onCityChange = { city = it }, onNext)
        3 -> SupplierStep4(
            name, email, password, cnpj,
            onNameChange = { name = it },
            onEmailChange = { email = it },
            onPasswordChange = { password = it },
            onCNPJChange = { cnpj = it },
            onFinish = onFinish
        )
    }
}

@Composable
fun SupplierStep1(onNext: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Qual tipo de solução sua empresa oferece?", style = MaterialTheme.typography.headlineSmall)

        Button(onClick = { /* Salvar a opção de Aluguel de placas solares */ onNext() }, modifier = Modifier.fillMaxWidth()) {
            Text("Aluguel de placas solares")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Salvar a opção de Venda de sistemas completos */ onNext() }, modifier = Modifier.fillMaxWidth()) {
            Text("Venda de sistemas completos")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Salvar a opção de Investimento em projetos específicos */ onNext() }, modifier = Modifier.fillMaxWidth()) {
            Text("Investimento em projetos específicos")
        }
    }
}

@Composable
fun SupplierStep2(onNext: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Quais imóveis você prefere atender?", style = MaterialTheme.typography.headlineSmall)

        Button(onClick = { /* Salvar a opção de Residências */ onNext() }, modifier = Modifier.fillMaxWidth()) {
            Text("Residências")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Salvar a opção de Galpões */ onNext() }, modifier = Modifier.fillMaxWidth()) {
            Text("Galpões")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Salvar a opção de Indústrias */ onNext() }, modifier = Modifier.fillMaxWidth()) {
            Text("Indústrias")
        }
    }
}

@Composable
fun SupplierStep3(city: String, onCityChange: (String) -> Unit, onNext: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Onde você gostaria de atuar? Informe a cidade.", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = city,
            onValueChange = onCityChange,
            label = { Text("Cidade") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNext, modifier = Modifier.fillMaxWidth()) {
            Text("Próximo")
        }
    }
}

@Composable
fun SupplierStep4(
    name: String,
    email: String,
    password: String,
    cnpj: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onCNPJChange: (String) -> Unit,
    onFinish: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Digite os dados básicos do fornecedor.", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Nome da empresa") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = cnpj,
            onValueChange = onCNPJChange,
            label = { Text("CNPJ") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            registerUser(
                email = email,
                password = password,
                additionalData = mapOf(
                    "name" to name,
                    "cnpj" to cnpj,
                    "city" to city // Adicione outras informações conforme necessário
                ),
                collection = "suppliers",
                onSuccess = onFinish,
                onError = { error -> /* Exibir mensagem de erro */ }
            )
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Finalizar Cadastro")
        }
    }
}
