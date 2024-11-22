package com.solarsync.solarapp.ui.screens.supplier

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SupplierRegisterViewModel : ViewModel() {
    private val _currentStep = mutableStateOf(0)
    val currentStep: State<Int> = _currentStep

    private val _formData = mutableStateOf(SupplierFormData())
    val formData: State<SupplierFormData> = _formData

    fun updateStep(step: Int) {
        _currentStep.value = step
    }

    fun updateFormData(data: SupplierFormData) {
        _formData.value = data
    }

    fun registerSupplier(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        auth.createUserWithEmailAndPassword(formData.value.email, formData.value.password)
            .addOnSuccessListener { result ->
                val userId = result.user?.uid ?: return@addOnSuccessListener

                db.collection("suppliers")
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

data class SupplierFormData(
    val solutionType: String = "",
    val preferredProperties: String = "",
    val operationArea: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val cnpj: String = "",
)
