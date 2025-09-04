package com.mpauli.feature.overviewscreen.domain.model

import com.mpauli.core.models.Thought
import java.time.LocalDate

internal data class ThoughtItemState(val date: LocalDate)

internal fun Thought.toItemState() = ThoughtItemState(date)
