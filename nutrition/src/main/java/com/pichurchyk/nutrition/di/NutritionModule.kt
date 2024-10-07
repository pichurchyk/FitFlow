package com.pichurchyk.nutrition.di

import com.pichurchyk.fitflow.common.preferences.AuthPreferencesActions
import com.pichurchyk.nutrition.repository.NutritionRepository
import com.pichurchyk.nutrition.database.NutritionDatabase
import com.pichurchyk.nutrition.database.repository.NutritionDatabaseRepositoryImpl
import com.pichurchyk.nutrition.remote.repository.NutritionRemoteRepositoryImpl
import com.pichurchyk.nutrition.remote.source.NutritionRemoteDataSource
import com.pichurchyk.nutrition.usecase.GetDailyInfoUseCase
import com.pichurchyk.nutrition.usecase.SaveIntakeUseCase
import com.pichurchyk.supabase.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val nutritionModule = module {

    single {
        val httpLogger = co.touchlab.kermit.Logger.withTag("Nutrition")

        val authPreferences: AuthPreferencesActions = get()
        val accessToken = runBlocking {
            return@runBlocking authPreferences.getAccessToken()
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

            ResponseObserver { response ->
                httpLogger.d { "HTTP status: ${response.status.value}" }
            }

            defaultRequest {
                url("${BuildConfig.SUPABASE_URL}/rest/v1/")
                header("apikey", BuildConfig.SUPABASE_ANON_KEY)
                header(HttpHeaders.Authorization, "Bearer $accessToken")
            }
        }
    }

    single { NutritionRemoteDataSource(get()) }
    single { NutritionDatabase(get()) }
    single<NutritionRepository>(qualifier = named<Qualifiers.RemoteRepository>()) { NutritionRemoteRepositoryImpl(get()) }
    single<NutritionRepository>(qualifier = named<Qualifiers.DatabaseRepository>()) { provideNutritionDatabaseRepository(get()) }

//    Use Cases
    single { SaveIntakeUseCase(get()) }
    single { GetDailyInfoUseCase(get(qualifier = named<Qualifiers.RemoteRepository>())) }

}
private fun provideNutritionDatabaseRepository(database: NutritionDatabase): NutritionRepository {
    return NutritionDatabaseRepositoryImpl(database.dao())
}
