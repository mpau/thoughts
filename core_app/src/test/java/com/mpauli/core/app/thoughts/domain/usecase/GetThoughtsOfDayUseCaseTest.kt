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

internal class GetThoughtsOfDayUseCaseTest {

    private val thoughtsRepository: ThoughtsRepository = mock()
    private val getThoughtsOfDayUseCase = GetThoughtsOfDayUseCase(thoughtsRepository)

    @Test
    fun `should get thoughts of a day`() {
        // Given
        val date = LocalDate.now()
        val thoughts = flowOf(
            listOf(
                Thought(
                    id = 1L,
                    date = date,
                    title = "test title 1",
                    text = "test text 1"
                ),
                Thought(
                    id = 2L,
                    date = date,
                    title = "test title 2",
                    text = "test text 2"
                )
            )
        )
        whenever(thoughtsRepository.getThoughtsOfDay(date)) doReturn thoughts

        // When
        val result = getThoughtsOfDayUseCase.run(date)

        // Then
        result shouldBeEqualTo thoughts
    }
}
