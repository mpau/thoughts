package com.mpauli.core.apodapi.data

import com.mpauli.core.apodapi.data.model.toDomain
import com.mpauli.core.models.Apod
import com.mpauli.core.network.BuildConfig
import com.mpauli.core.network.client.KtorClientBuilder
import de.jensklingenberg.ktorfit.Ktorfit
import java.time.LocalDate

class ApodApi internal constructor(
    ktorClientBuilder: KtorClientBuilder,
    private val baseUrl: String = BuildConfig.NASA_BASE_URL,
    private val apiKey: String = BuildConfig.NASA_API_KEY
) {

    private val service: ApodApiService = Ktorfit.Builder()
        .baseUrl(baseUrl)
        .httpClient(ktorClientBuilder.buildKtorClient())
        .build()
        .createApodApiService()

    suspend fun getApod(date: LocalDate): Apod = service.getApod(
        date = date.toString(),
        apiKey = apiKey
    ).toDomain()
}
