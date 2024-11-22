package com.solarsync.solarapp.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _userData = MutableStateFlow<Map<String, Any>?>(null)
    val userData: StateFlow<Map<String, Any>?> = _userData

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _aboutMe = MutableStateFlow("")
    val aboutMe: StateFlow<String> = _aboutMe

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val userId = auth.currentUser?.uid ?: return

        _isLoading.value = true

        // Check both collections
        viewModelScope.launch {
            db.collection("clients").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        _userData.value = document.data
                        _aboutMe.value = document.getString("aboutMe") ?: ""
                    } else {
                        // If not found in clients, check suppliers
                        db.collection("suppliers").document(userId).get()
                            .addOnSuccessListener { supplierDoc ->
                                _userData.value = supplierDoc.data
                                _aboutMe.value = supplierDoc.getString("aboutMe") ?: ""
                            }
                    }
                    _isLoading.value = false
                }
                .addOnFailureListener {
                    _isLoading.value = false
                }
        }
    }

    fun updateAboutMe(newAboutMe: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val userId = auth.currentUser?.uid ?: return

        db.collection("clients").document(userId).update("aboutMe", newAboutMe)
            .addOnSuccessListener {
                _aboutMe.value = newAboutMe
                onSuccess()
            }
            .addOnFailureListener { exception ->
                // If not found in clients, try suppliers
                db.collection("suppliers").document(userId).update("aboutMe", newAboutMe)
                    .addOnSuccessListener {
                        _aboutMe.value = newAboutMe
                        onSuccess()
                    }
                    .addOnFailureListener {
                        onError(exception.message ?: "Erro ao atualizar")
                    }
            }
    }

    fun deleteAboutMe(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val userId = auth.currentUser?.uid ?: return

        db.collection("clients").document(userId).update("aboutMe", "")
            .addOnSuccessListener {
                _aboutMe.value = ""
                onSuccess()
            }
            .addOnFailureListener { exception ->
                // If not found in clients, try suppliers
                db.collection("suppliers").document(userId).update("aboutMe", "")
                    .addOnSuccessListener {
                        _aboutMe.value = ""
                        onSuccess()
                    }
                    .addOnFailureListener {
                        onError(exception.message ?: "Erro ao deletar")
                    }
            }
    }

    fun signOut(onComplete: () -> Unit) {
        FirebaseAuth.getInstance().signOut()
        onComplete()
    }
}
