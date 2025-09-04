package com.mpauli.feature.dayscreen.domain.model

import com.mpauli.core.models.Thought
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class ThoughtItemStateTest {

    private val id = 1L
    private val title = "test title"
    private val text = "test text"
    private val testThought = Thought(
        id = id,
        date = LocalDate.now(),
        title = title,
        text = text
    )
    private val resultThoughtItemState = ThoughtItemState(
        id = id,
        title = title,
        text = text
    )

    @Test
    fun `should map Thought to ThoughtItemState`() {
        // When
        val result = testThought.toItemState()

        // Then
        result shouldBeEqualTo resultThoughtItemState
    }
}
