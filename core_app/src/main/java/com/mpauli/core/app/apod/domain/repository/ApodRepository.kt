package com.mpauli.core.app.apod.domain.repository

import com.mpauli.base.data.tryAsResult
import com.mpauli.core.apodapi.data.ApodApi
import com.mpauli.core.models.Apod
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MemoryPolicy
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.impl.extensions.get
import java.time.LocalDate
import kotlin.time.Duration.Companion.days

internal class ApodRepository(private val apodApi: ApodApi) {

    private val apodStore: Store<ApodRequest, Apod> =
        StoreBuilder
            .from(
                fetcher = Fetcher.of<ApodRequest, Apod> { request ->
                    apodApi.getApod(request.date)
                }
            )
            .cachePolicy(
                MemoryPolicy.builder<ApodRequest, Apod>()
                    .setMaxSize(APOD_CACHE_SIZE)
                    .setExpireAfterWrite(1.days)
                    .build()
            )
            .build()

    suspend fun get(date: LocalDate): Result<Apod> = tryAsResult {
        apodStore.get(ApodRequest(date))
    }

    private data class ApodRequest(val date: LocalDate)

    companion object {
        private const val APOD_CACHE_SIZE = 1L
    }
}
