package com.mpauli.core.apodapi.data.model

import com.mpauli.core.models.Apod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
internal data class ApodDto(
    @SerialName("copyright")
    val copyright: String? = null,
    @SerialName("date")
    val date: String,
    @SerialName("explanation")
    val explanation: String,
    @SerialName("hdurl")
    val hdUrl: String? = null,
    @SerialName("media_type")
    val mediaType: String,
    @SerialName("service_version")
    val serviceVersion: String,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String?
)

internal fun ApodDto.toDomain(): Apod = Apod(
    copyright = copyright.orEmpty().trim(),
    date = LocalDate.parse(date),
    explanation = explanation.replace(MULTI_SPACE, " "),
    hdUrl = hdUrl ?: url.orEmpty(),
    mediaType = mediaType,
    serviceVersion = serviceVersion,
    title = title,
    url = url.orEmpty()
)

private val MULTI_SPACE = Regex(" {2,}")
