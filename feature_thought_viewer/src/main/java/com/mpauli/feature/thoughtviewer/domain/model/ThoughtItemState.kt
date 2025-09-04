package com.mpauli.feature.thoughtviewer.domain.model

import com.mpauli.core.models.Thought
import java.time.LocalDate

internal data class ThoughtItemState(
    val id: Long,
    val date: LocalDate,
    val title: String,
    val text: String
)

internal fun Thought.toItemState() = ThoughtItemState(
    id = id,
    date = date,
    title = title,
    text = text
)

internal fun ThoughtItemState.toDomain() = Thought(
    id = id,
    date = date,
    title = title,
    text = text
)
