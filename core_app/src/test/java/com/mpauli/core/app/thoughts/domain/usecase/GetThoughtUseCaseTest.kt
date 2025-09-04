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

internal class GetThoughtUseCaseTest {

    private val thoughtsRepository: ThoughtsRepository = mock()
    private val getThoughtUseCase = GetThoughtUseCase(thoughtsRepository)

    @Test
    fun `should get thought with id`() {
        // Given
        val id = 1L
        val thought = flowOf(
            Thought(
                id = id,
                date = LocalDate.now(),
                title = "test title 1",
                text = "test text 1"
            )
        )
        whenever(thoughtsRepository.getThought(id)) doReturn thought

        // When
        val result = getThoughtUseCase.run(id)

        // Then
        result shouldBeEqualTo thought
    }
}
