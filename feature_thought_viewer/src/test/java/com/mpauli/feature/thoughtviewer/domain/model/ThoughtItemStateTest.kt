package com.mpauli.feature.thoughtviewer.domain.model

import com.mpauli.core.models.Thought
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class ThoughtItemStateTest {

    private val id = 1L
    private val date = LocalDate.now()
    private val title = "test title"
    private val text = "test text"
    private val testThought = Thought(
        id = id,
        date = date,
        title = title,
        text = text
    )
    private val testThoughtItemState = ThoughtItemState(
        id = id,
        date = date,
        title = title,
        text = text
    )

    @Test
    fun `should map Thought to ThoughtItemState`() {
        // When
        val result = testThought.toItemState()

        // Then
        result shouldBeEqualTo testThoughtItemState
    }

    @Test
    fun `should map ThoughtItemState to Thought`() {
        // When
        val result = testThoughtItemState.toDomain()

        // Then
        result shouldBeEqualTo testThought
    }
}
