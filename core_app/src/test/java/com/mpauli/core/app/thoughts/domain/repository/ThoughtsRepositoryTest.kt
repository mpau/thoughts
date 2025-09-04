package com.mpauli.core.app.thoughts.domain.repository

import com.mpauli.core.app.thoughts.db.ThoughtsDatabase
import com.mpauli.core.models.Thought
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

internal class ThoughtsRepositoryTest {

    private val thoughtsDatabase: ThoughtsDatabase = mock()
    private val thoughtsRepository = ThoughtsRepository(thoughtsDatabase)

    private val id1 = 1L
    private val date1 = LocalDate.now()
    private val testThought1 = Thought(
        id = id1,
        date = LocalDate.now(),
        title = "test title 1",
        text = "test text 1"
    )
    private val testThought2 = Thought(
        id = 2L,
        date = LocalDate.now(),
        title = "test title 2",
        text = "test text 2"
    )

    @Test
    fun `should get all thoughts`() {
        // Given
        val thoughts: Flow<List<Thought>> = flowOf(
            listOf(
                testThought1,
                testThought2
            )
        )
        whenever(thoughtsDatabase.getAllThoughts()) doReturn thoughts

        // When
        val result = thoughtsRepository.getAllThoughts()

        // Then
        result shouldBeEqualTo thoughts
    }

    @Test
    fun `should get thoughts of a day`() {
        // Given
        val thoughts: Flow<List<Thought>> = flowOf(
            listOf(
                testThought1,
                testThought2.copy(date = date1)
            )
        )
        whenever(thoughtsDatabase.getThoughtsOfDay(date1)) doReturn thoughts

        // When
        val result = thoughtsRepository.getThoughtsOfDay(date1)

        // Then
        result shouldBeEqualTo thoughts
    }

    @Test
    fun `should get thought with id`() {
        // Given
        val thoughtFlow = flowOf(testThought1)
        whenever(thoughtsDatabase.getThought(id1)) doReturn thoughtFlow

        // When
        val result = thoughtsRepository.getThought(id1)

        // Then
        result shouldBeEqualTo thoughtFlow
    }

    @Test
    fun `should insert or update thought`() = runTest {
        // When
        thoughtsRepository.upsertThought(testThought1)

        // Then
        verify(thoughtsDatabase).upsertThought(testThought1)
    }

    @Test
    fun `should delete thought`() = runTest {
        // When
        thoughtsRepository.deleteThought(testThought1)

        // Then
        verify(thoughtsDatabase).deleteThought(testThought1)
    }
}
