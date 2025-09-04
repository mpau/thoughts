package com.mpauli.core.models

import java.time.LocalDate

data class Thought(
    val id: Long,
    val date: LocalDate,
    val title: String,
    val text: String
)
