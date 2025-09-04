package com.mpauli.feature.overviewscreen.domain.model

import com.mpauli.core.models.Thought
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class ThoughtItemStateTest {

    private val date = LocalDate.now()
    private val testThought = Thought(
        id = 1L,
        date = date,
        title = "test title",
        text = "test text"
    )
    private val resultThoughtItemState = ThoughtItemState(date)

    @Test
    fun `should map Thought to ThoughtItemState`() {
        // When
        val result = testThought.toItemState()

        // Then
        result shouldBeEqualTo resultThoughtItemState
    }
}
