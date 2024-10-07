package com.pichurchyk.fitflow.auth.datasource

import android.util.Log
import com.pichurchyk.fitflow.auth.model.SignInResult
import com.pichurchyk.fitflow.common.preferences.AuthPreferences
import com.pichurchyk.fitflow.common.preferences.AuthPreferencesActions
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
    private val supabaseClient: SupabaseClient,
    private val preferences: AuthPreferencesActions
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

                        preferences.setAccessToken(it.session.accessToken)
                        preferences.setUserUid(it.session.user?.id)
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
            supabaseClient.auth.signOut()

            preferences.setAccessToken(null)
            preferences.setUserUid(null)

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