package com.solarsync.solarapp.ui.screens.client

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ClientRegisterViewModel : ViewModel() {
    private val _currentStep = mutableStateOf(0)
    val currentStep: State<Int> = _currentStep

    private val _formData = mutableStateOf(ClientFormData())
    val formData: State<ClientFormData> = _formData

    fun updateStep(step: Int) {
        _currentStep.value = step
    }

    fun updateFormData(data: ClientFormData) {
        _formData.value = data
    }

    fun registerClient(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        auth.createUserWithEmailAndPassword(formData.value.email, formData.value.password)
            .addOnSuccessListener { result ->
                val userId = result.user?.uid ?: return@addOnSuccessListener

                db.collection("clients")
                    .document(userId)
                    .set(formData.value)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener {
                        onError(it.message ?: "Erro ao salvar dados")
                    }
            }
            .addOnFailureListener {
                onError(it.message ?: "Erro ao criar usuário")
            }
    }
}

data class ClientFormData(
    val installationType: String = "",
    val energyConsumption: String = "",
    val location: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val cpf: String = "",
    val city: String = "",
    val address: String = "",
    val monthlyBill: String = ""
)