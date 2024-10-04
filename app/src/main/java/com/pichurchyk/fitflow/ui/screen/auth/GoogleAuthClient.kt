package com.pichurchyk.fitflow.ui.screen.auth

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.pichurchyk.fitflow.BuildConfig

class GoogleAuthClient(
    private val context: Context,
) {

    private val credentialManager = CredentialManager.create(context)

    val signedInAccount = mutableStateOf<AuthCredential?>(null)

    private val googleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
        .build()

    private val credentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    suspend fun startSignIn() {
        try {
            val result = credentialManager.getCredential(
                context = context,
                request = credentialRequest
            )

            val credential = result.credential

            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

            val googleIdToken = googleIdTokenCredential.idToken

            val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

            signedInAccount.value = googleCredentials

            Log.i(TAG, "GoogleSignInCredentials received")
        } catch (e: GetCredentialException) {
            Log.e(TAG, e.message ?: "Error while getting credentials")
        } catch (e: GoogleIdTokenParsingException) {
            Log.e(TAG, e.message ?: "Error while parsing GoogleIdToken")
        }
    }

    companion object {
        private const val TAG = "GoogleAuthClient"
    }
}