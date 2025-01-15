package com.pichurchyk.supabase.di

import com.pichurchyk.fitflow.common.preferences.AuthPreferencesActions
import com.pichurchyk.supabase.BuildConfig
import com.pichurchyk.supabase.model.RefreshTokenPayload
import com.pichurchyk.supabase.model.RefreshTokenResponse
import com.pichurchyk.supabase.supabaseClient
import io.github.jan.supabase.SupabaseClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val supabaseModule = module {
    single<SupabaseClient> { supabaseClient }

    single {
        val httpLogger = co.touchlab.kermit.Logger.withTag("SUPABASE")

        val authPreferences: AuthPreferencesActions = get()
        val accessToken = runBlocking {
            return@runBlocking authPreferences.getAccessToken()
        }
        val refreshToken = runBlocking {
            return@runBlocking authPreferences.getRefreshToken()
        }

        HttpClient {
            expectSuccess = true

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(Resources)

            Logging {
                logger = object : Logger {
                    override fun log(message: String) {
                        httpLogger.i { message }
                    }
                }

                level = LogLevel.ALL
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        if (accessToken.isEmpty() && refreshToken.isEmpty()) {
                            return@loadTokens null
                        }
                        BearerTokens(
                            accessToken,
                            refreshToken
                        )
                    }

                    refreshTokens {
                        try {
                            val refreshUrl = "${BuildConfig.SUPABASE_URL}/auth/v1/token?grant_type=refresh_token"
                            val refreshTokenResponse: RefreshTokenResponse = client
                                .post(refreshUrl) {
                                    markAsRefreshTokenRequest()
                                    setBody(
                                        RefreshTokenPayload(refreshToken)
                                    )
                                    header("apikey", BuildConfig.SUPABASE_ANON_KEY)
                                    header(HttpHeaders.ContentType, "application/json")
                                }.body()

                            authPreferences.setAccessToken(refreshTokenResponse.accessToken)
                            authPreferences.setRefreshToken(refreshTokenResponse.refreshToken)

                            BearerTokens(
                                refreshTokenResponse.accessToken,
                                refreshTokenResponse.refreshToken
                            )

                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }
                    }
                }
            }


            ResponseObserver { response ->
                httpLogger.d { "HTTP status: ${response.status.value}" }
            }

            defaultRequest {
                url("${BuildConfig.SUPABASE_URL}/rest/v1/")
                header("apikey", BuildConfig.SUPABASE_ANON_KEY)
                header(HttpHeaders.Authorization, "Bearer $accessToken")
                header(HttpHeaders.ContentType, "application/json")
            }
        }
    }
}