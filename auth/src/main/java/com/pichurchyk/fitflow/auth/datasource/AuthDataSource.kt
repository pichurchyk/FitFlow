package com.pichurchyk.fitflow.auth.datasource

import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pichurchyk.fitflow.auth.model.SignInResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

internal class AuthDataSource {
    private val firebaseAuth = Firebase.auth

    fun checkIsUserAuthenticated(): Flow<Boolean> = callbackFlow {
        try {
            val authenticatedUser = firebaseAuth.currentUser

            trySend(authenticatedUser != null)

            awaitClose()
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "Error occurred")
        }
    }

    fun signIn(authCredential: AuthCredential): Flow<SignInResult> = callbackFlow {
        try {
            firebaseAuth.signInWithCredential(authCredential)
                .addOnSuccessListener { result ->
                    result.user?.let { user ->
                        trySend(
                            SignInResult.Success(user)
                        )
                    }
                }
                .addOnFailureListener {
                    trySend(
                        SignInResult.Error(it.message ?: "Error while signing in")
                    )
                }

            awaitClose()
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "Error occurred")
        }
    }

    fun signOut(): Flow<Unit> = callbackFlow {
        try {
            firebaseAuth.signOut().also {
                trySend(Unit)
            }

            awaitClose()
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "Error occurred")
        }
    }

    companion object {
        private const val TAG = "AuthDataSource"
    }
}