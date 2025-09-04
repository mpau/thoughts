package com.mpauli.feature.overviewscreen.viewmodel

import com.mpauli.feature.overviewscreen.domain.model.ThoughtItemState
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewAction
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class OverviewScreenStateProcessorTest {

    @Test
    fun `should process ViewAction#UpdateThoughts`() = runTest {
        // Given
        val date = LocalDate.now()
        val thoughtsList = listOf(
            ThoughtItemState(date),
            ThoughtItemState(date),
            ThoughtItemState(date),
        )
        val viewAction = ViewAction.UpdateThoughts(thoughtsList)

        val overviewScreenStateProcessor = OverviewScreenStateProcessor()

        // When
        val resultState = overviewScreenStateProcessor.process(viewAction)

        // Then
        resultState.thoughts shouldBeEqualTo thoughtsList
    }
}
