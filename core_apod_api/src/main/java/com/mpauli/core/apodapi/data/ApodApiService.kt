package com.mpauli.core.apodapi.data

import com.mpauli.core.apodapi.data.model.ApodDto
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

internal interface ApodApiService {

    @GET("planetary/apod")
    suspend fun getApod(
        @Query("date") date: String,
        @Query("api_key") apiKey: String,
    ): ApodDto
}
