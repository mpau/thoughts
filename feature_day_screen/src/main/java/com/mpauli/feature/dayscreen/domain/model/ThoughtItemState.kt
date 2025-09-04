package com.mpauli.feature.dayscreen.domain.model

import com.mpauli.core.models.Thought

internal data class ThoughtItemState(
    val id: Long,
    val title: String,
    val text: String
)

internal fun Thought.toItemState() = ThoughtItemState(
    id = id,
    title = title,
    text = text
)
