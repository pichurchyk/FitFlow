package com.pichurchyk.fitflow.common.preferences

import kotlinx.coroutines.flow.Flow

interface AuthPreferencesActions {
    suspend fun setAccessToken(accessToken: String?)
    suspend fun setUserUid(uid: String?)

    suspend fun getAccessToken(): String
    suspend fun getUserUid(): Flow<String>
}