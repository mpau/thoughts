package com.mpauli.core.app.thoughts.domain.usecase

import com.mpauli.core.app.thoughts.domain.repository.ThoughtsRepository
import com.mpauli.core.models.Thought
import kotlinx.coroutines.flow.flowOf
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate

internal class GetAllThoughtsUseCaseTest {

    private val thoughtsRepository: ThoughtsRepository = mock()
    private val getAllThoughtsUseCase = GetAllThoughtsUseCase(thoughtsRepository)

    @Test
    fun `should get all thoughts`() {
        // Given
        val thoughts = flowOf(
            listOf(
                Thought(
                    id = 1L, date = LocalDate.now(), title = "test title 1", text = "test text 1"
                ), Thought(
                    id = 2L, date = LocalDate.now(), title = "test title 2", text = "test text 2"
                )
            )
        )
        whenever(thoughtsRepository.getAllThoughts()) doReturn thoughts

        // When
        val result = getAllThoughtsUseCase.run()

        // Then
        result shouldBeEqualTo thoughts
    }
}
