package com.solarsync.solarapp.ui.screens.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun updateEmail(value: String) {
        _email.value = value
    }

    fun updatePassword(value: String) {
        _password.value = value
    }

    fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.value, password.value)
                    .addOnSuccessListener {
                        _isLoading.value = false
                        onSuccess()
                    }
                    .addOnFailureListener { exception ->
                        _isLoading.value = false
                        onError(exception.localizedMessage ?: "Erro ao fazer login")
                    }
            } catch (e: Exception) {
                _isLoading.value = false
                onError(e.localizedMessage ?: "Erro ao fazer login")
            }
        }
    }
}