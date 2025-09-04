package com.mpauli.core.network.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.util.concurrent.TimeUnit

class KtorClientBuilder {

    fun buildKtorClient(): HttpClient = HttpClient(OkHttp) {
        expectSuccess = true

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    explicitNulls = false
                }
            )
        }

        install(Logging) {
            level = LogLevel.INFO
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.d("Ktor: $message")
                }
            }
        }

        install(HttpTimeout) {
            requestTimeoutMillis = TimeUnit.SECONDS.toMillis(20)
            connectTimeoutMillis = TimeUnit.SECONDS.toMillis(10)
            socketTimeoutMillis = TimeUnit.SECONDS.toMillis(20)
        }

        install(HttpRequestRetry) {
            exponentialDelay()
            retryOnServerErrors(maxRetries = 2)
        }
    }
}
