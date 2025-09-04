package com.mpauli.core.models

import java.time.LocalDate

data class Apod(
    val copyright: String,
    val date: LocalDate,
    val explanation: String,
    val hdUrl: String,
    val mediaType: String,
    val serviceVersion: String,
    val title: String,
    val url: String
)
