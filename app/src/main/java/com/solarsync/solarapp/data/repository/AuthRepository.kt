package com.solarsync.solarapp.data.repository

import com.solarsync.solarapp.data.datasource.FirebaseDataSource
import com.solarsync.solarapp.data.model.Client
import com.solarsync.solarapp.data.model.Supplier
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {
    suspend fun signUpClient(
        email: String,
        password: String,
        client: Client
    ): Result<String> {
        return firebaseDataSource.signUp(email, password).map { userId ->
            firebaseDataSource.saveClient(client.copy(userId = userId))
            userId
        }
    }

    suspend fun signUpSupplier(
        email: String,
        password: String,
        supplier: Supplier
    ): Result<String> {
        return firebaseDataSource.signUp(email, password).map { userId ->
            firebaseDataSource.saveSupplier(supplier.copy(userId = userId))
            userId
        }
    }

    suspend fun signIn(email: String, password: String): Result<String> {
        return firebaseDataSource.signIn(email, password)
    }
}