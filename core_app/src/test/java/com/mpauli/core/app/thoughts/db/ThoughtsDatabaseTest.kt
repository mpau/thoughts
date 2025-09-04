package com.mpauli.core.app.thoughts.db

import com.mpauli.core.app.thoughts.db.model.ThoughtEntity
import com.mpauli.core.app.thoughts.db.model.toDomain
import com.mpauli.core.app.thoughts.db.model.toEntity
import com.mpauli.core.models.Thought
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

internal class ThoughtsDatabaseTest {

    private val mockThoughtsDao: ThoughtsDao = mock()
    private val mockDatabase: ThoughtsRoomDatabase = mock {
        on { getThoughtsDao() } doReturn mockThoughtsDao
    }

    private val testDispatcher = StandardTestDispatcher()
    private val thoughtsDatabase = ThoughtsDatabase(
        db = mockDatabase,
        defaultDispatcher = testDispatcher
    )

    private val id1 = 1L
    private val date1 = LocalDate.now().minusDays(1L)
    private val date2 = LocalDate.now()
    private val title1 = "test title 1"
    private val text1 = "test text 1"
    private val testThoughtEntity1 = ThoughtEntity(
        id = id1,
        date = date1,
        title = title1,
        text = text1
    )
    private val testThought = Thought(
        id = id1,
        date = date1,
        title = title1,
        text = text1
    )
    private val testThoughtEntity2 = ThoughtEntity(
        id = 2L,
        date = date2,
        title = "test title 2",
        text = "test text 2"
    )

    @Test
    fun `should get all thoughts from DB`() = runTest(testDispatcher) {
        // Given
        val thoughtEntities = flowOf(
            listOf(
                testThoughtEntity1,
                testThoughtEntity2
            )
        )
        whenever(mockThoughtsDao.getAllThoughts()) doReturn thoughtEntities

        // When
        val result = thoughtsDatabase.getAllThoughts().first()

        // Then
        val expectedThoughts = thoughtEntities.first().map { it.toDomain() }
        result shouldBeEqualTo expectedThoughts
    }

    @Test
    fun `should get thoughts of a day from DB`() = runTest(testDispatcher) {
        // Given
        val thoughtEntities = flowOf(
            listOf(
                testThoughtEntity1,
                testThoughtEntity2.copy(date = date1)
            )
        )
        whenever(mockThoughtsDao.getThoughtsOfDay(date1)) doReturn thoughtEntities

        // When
        val result = thoughtsDatabase.getThoughtsOfDay(date1).first()

        // Then
        val expectedThoughts = thoughtEntities.first().map { it.toDomain() }
        result shouldBeEqualTo expectedThoughts
    }

    @Test
    fun `should get thought with id from DB`() = runTest(testDispatcher) {
        // Given
        val thoughtEntity = flowOf(testThoughtEntity1)
        whenever(mockThoughtsDao.getThought(id1)) doReturn thoughtEntity

        // When
        val result = thoughtsDatabase.getThought(id1).first()

        // Then
        val expectedThought = thoughtEntity.first().toDomain()
        result shouldBeEqualTo expectedThought
    }

    @Test
    fun `should be empty when thought id not in DB`() = runTest(testDispatcher) {
        // Given
        whenever(mockThoughtsDao.getThought(id1)) doReturn flowOf(null)

        // When
        val result = thoughtsDatabase.getThought(id1).firstOrNull()

        // Then
        result shouldBeEqualTo null
    }

    @Test
    fun `should insert thought into DB`() = runTest {
        // Given
        val thought = testThought.copy(id = 0L)

        // When
        thoughtsDatabase.upsertThought(thought)

        // Then
        verify(mockThoughtsDao).upsert(thought.toEntity())
    }

    @Test
    fun `should update thought in DB`() = runTest {
        // Given
        val thought = testThought

        // When
        thoughtsDatabase.upsertThought(thought)

        // Then
        verify(mockThoughtsDao).upsert(thought.toEntity())
    }

    @Test
    fun `should delete thought in DB`() = runTest {
        // Given
        val thought = testThought

        // When
        thoughtsDatabase.deleteThought(thought)

        // Then
        verify(mockThoughtsDao).delete(thought.toEntity())
    }
}
