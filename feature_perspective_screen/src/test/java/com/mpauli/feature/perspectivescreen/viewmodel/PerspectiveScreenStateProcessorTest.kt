package com.mpauli.feature.perspectivescreen.viewmodel

import com.mpauli.feature.perspectivescreen.domain.model.ApodItemState
import com.mpauli.feature.perspectivescreen.viewmodel.model.ViewAction
import com.mpauli.feature.perspectivescreen.viewmodel.model.ViewState
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class PerspectiveScreenStateProcessorTest {

    @Test
    fun `should process ViewAction#ShowLoading`() = runTest {
        // Given
        val viewAction = ViewAction.ShowLoading

        val perspectiveScreenStateProcessor = PerspectiveScreenStateProcessor()

        // When
        val resultState = perspectiveScreenStateProcessor.process(viewAction)

        // Then
        resultState shouldBeEqualTo ViewState.DEFAULT.copy(
            apodItemState = ViewState.DEFAULT.apodItemState,
            isLoading = true,
            isError = false
        )
    }

    @Test
    fun `should process ViewAction#ShowError`() = runTest {
        // Given
        val viewAction = ViewAction.ShowError

        val perspectiveScreenStateProcessor = PerspectiveScreenStateProcessor()

        // When
        val resultState = perspectiveScreenStateProcessor.process(viewAction)

        // Then
        resultState shouldBeEqualTo ViewState.DEFAULT.copy(
            apodItemState = ViewState.DEFAULT.apodItemState,
            isLoading = false,
            isError = true
        )
    }

    @Test
    fun `should process ViewAction#UpdateApod`() = runTest {
        // Given
        val apodItemState = ApodItemState(
            copyright = "copyright",
            date = LocalDate.now(),
            explanation = "explanation",
            hdUrl = "hdUrl",
            title = "title",
            url = "url"
        )
        val viewAction = ViewAction.UpdateApod(apodItemState)

        val perspectiveScreenStateProcessor = PerspectiveScreenStateProcessor()

        // When
        val resultState = perspectiveScreenStateProcessor.process(viewAction)

        // Then
        resultState shouldBeEqualTo ViewState(
            apodItemState = apodItemState,
            isLoading = false,
            isError = false
        )
    }
}
