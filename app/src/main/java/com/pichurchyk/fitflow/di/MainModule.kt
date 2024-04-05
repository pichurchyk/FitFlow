package com.pichurchyk.fitflow.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import co.touchlab.kermit.Logger as KermitLogger


@OptIn(ExperimentalSerializationApi::class)
val mainModule = module {
    single {
        val httpLogger = KermitLogger.withTag("Main Module Http Client")

        HttpClient(engine = get()) {
            expectSuccess = true

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                        explicitNulls = true
                    }
                )
            }

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
        }
    }
}