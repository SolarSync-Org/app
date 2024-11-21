package com.solarsync.solarapp.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.solarsync.solarapp.data.model.Client
import com.solarsync.solarapp.data.model.Supplier
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseDataSource @Inject constructor() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun signUp(email: String, password: String): Result<String> = try {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        Result.success(result.user?.uid ?: throw Exception("User ID not found"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun signIn(email: String, password: String): Result<String> = try {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        Result.success(result.user?.uid ?: throw Exception("User ID not found"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun saveClient(client: Client): Result<Unit> = try {
        firestore.collection("clients")
            .document(client.userId)
            .set(client)
            .await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun saveSupplier(supplier: Supplier): Result<Unit> = try {
        firestore.collection("suppliers")
            .document(supplier.userId)
            .set(supplier)
            .await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
