package com.mpauli.feature.dayscreen.viewmodel

import com.mpauli.feature.dayscreen.domain.model.ThoughtItemState
import com.mpauli.feature.dayscreen.viewmodel.model.ViewAction
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

internal class DayScreenStateProcessorTest {

    @Test
    fun `should process ViewAction#UpdateThoughts`() = runTest {
        // Given
        val thoughtsList = listOf(
            ThoughtItemState(
                id = 1L,
                title = "test title 1",
                text = "test text 1"
            ),
            ThoughtItemState(
                id = 2L,
                title = "test title 2",
                text = "test text 2"
            ),
            ThoughtItemState(
                id = 3L,
                title = "test title 3",
                text = "test text 3"
            ),
        )
        val viewAction = ViewAction.UpdateThoughts(thoughtsList)

        val dayScreenStateProcessor = DayScreenStateProcessor()

        // When
        val resultState = dayScreenStateProcessor.process(viewAction)

        // Then
        resultState.thoughts shouldBeEqualTo thoughtsList
    }
}
