package com.mpauli.core.app.thoughts.domain.usecase

import com.mpauli.core.app.thoughts.domain.repository.ThoughtsRepository
import com.mpauli.core.models.Thought
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDate

internal class DeleteThoughtUseCaseTest {

    private val thoughtsRepository: ThoughtsRepository = mock()
    private val deleteThoughtUseCase = DeleteThoughtUseCase(thoughtsRepository)

    @Test
    fun `should delete thought`() = runTest {
        // Given
        val id = 1L
        val date = LocalDate.now()
        val title = "test title"
        val text = "test text"

        // When
        deleteThoughtUseCase.run(
            Thought(
                id = id,
                date = date,
                title = title,
                text = text
            )
        )

        // Then
        val captorEvent = argumentCaptor<Thought>()
        verify(thoughtsRepository).deleteThought(captorEvent.capture())
        captorEvent.firstValue.id shouldBeEqualTo id
        captorEvent.firstValue.date shouldBeEqualTo date
        captorEvent.firstValue.title shouldBeEqualTo title
        captorEvent.firstValue.text shouldBeEqualTo text
    }
}
