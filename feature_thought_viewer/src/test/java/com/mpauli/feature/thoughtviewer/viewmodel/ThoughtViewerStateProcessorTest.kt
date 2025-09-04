package com.mpauli.feature.thoughtviewer.viewmodel

import com.mpauli.feature.thoughtviewer.domain.model.ThoughtItemState
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewAction
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class ThoughtViewerStateProcessorTest {

    private val thoughtViewerStateProcessor = ThoughtViewerStateProcessor()

    @Test
    fun `should process ViewAction#UpdateThought`() = runTest {
        // Given
        val thoughtItemState = ThoughtItemState(
            id = 1L,
            date = LocalDate.now(),
            title = "Test title",
            text = "Test text"
        )
        val viewAction = ViewAction.UpdateThought(thoughtItemState)

        // When
        val resultState = thoughtViewerStateProcessor.process(viewAction)

        // Then
        resultState.thought shouldBeEqualTo thoughtItemState
    }

    @Test
    fun `should process ViewAction#UpdateEditState`() = runTest {
        // Given
        val viewAction = ViewAction.UpdateEditState(true)

        // When
        val resultState = thoughtViewerStateProcessor.process(viewAction)

        // Then
        resultState.editState shouldBeEqualTo true
    }
}
