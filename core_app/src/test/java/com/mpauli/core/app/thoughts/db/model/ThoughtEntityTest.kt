package com.mpauli.core.app.thoughts.db.model

import com.mpauli.core.models.Thought
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class ThoughtEntityTest {

    private val id = 1L
    private val date = LocalDate.now()
    private val title = "test title"
    private val text = "test text"
    private val testThoughtEntity = ThoughtEntity(
        id = id,
        date = date,
        title = title,
        text = text
    )
    private val testThought = Thought(
        id = id,
        date = date,
        title = title,
        text = text
    )

    @Test
    fun `should map ThoughtEntity to Thought`() {
        // When
        val result = testThoughtEntity.toDomain()

        // Then
        result shouldBeEqualTo testThought
    }

    @Test
    fun `should map Thought to ThoughtEntity`() {
        // Given
        val thought = testThought

        // When
        val result = thought.toEntity()

        // Then
        result shouldBeEqualTo testThoughtEntity
    }
}
