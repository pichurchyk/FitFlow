package com.pichurchyk.fitflow.auth.datasource

import android.util.Log
import com.pichurchyk.fitflow.auth.model.SignInResult
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first

internal class AuthDataSource(
    private val supabaseClient: SupabaseClient
) {
    fun getSignedInUser(): Flow<UserInfo?> = callbackFlow {
        try {
            trySend(supabaseClient.auth.currentUserOrNull())

            close()

            awaitClose()
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "Error occurred")
        }
    }

    fun signIn(googleIdToken: String): Flow<SignInResult> = callbackFlow {
        try {
            supabaseClient.auth.signInWith(IDToken) {
                idToken = googleIdToken
                provider = Google
            }

            supabaseClient.auth.sessionStatus.first {
                when (it) {
                    is SessionStatus.Authenticated -> {
                        trySend(
                            SignInResult.Success(it.session.user!!)
                        )
                    }

                    else -> {
                        trySend(
                            SignInResult.Error("Error while signing in")
                        )
                    }
                }

                close()
                true
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "Error occurred")
            trySend(
                SignInResult.Error("Error while signing in")
            )

            close()
        }

        awaitClose()
    }

    fun signOut(): Flow<Unit> = callbackFlow {
        try {

            close()

            awaitClose()
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "Error occurred")
        }
    }

    companion object {
        private const val TAG = "AuthDataSource"
    }
}