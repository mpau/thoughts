package com.mpauli.feature.perspectivescreen.domain.model

import com.mpauli.core.models.Apod
import java.time.LocalDate

internal data class ApodItemState(
    val copyright: String,
    val date: LocalDate,
    val explanation: String,
    val hdUrl: String,
    val title: String,
    val url: String
)

internal fun Apod.toItemState() = ApodItemState(
    copyright = copyright,
    date = date,
    explanation = explanation,
    hdUrl = hdUrl,
    title = title,
    url = url
)
