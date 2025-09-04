package com.mpauli.feature.overviewscreen.viewmodel.delegates

import com.mpauli.base.res.ResourceProvider
import com.mpauli.core.app.thoughts.domain.usecase.UpsertThoughtUseCase
import com.mpauli.core.models.Thought
import com.mpauli.core.test.TestScope
import com.mpauli.feature.base.mvi.MviPlugins
import com.mpauli.feature.base.ui.domain.model.FeedbackData
import com.mpauli.feature.overviewscreen.R
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewEffect
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

internal class ThoughtAddedViewModelDelegateTest {

    private val resourceProvider: ResourceProvider = mock()
    private val upsertThoughtUseCase: UpsertThoughtUseCase = mock()
    private val thoughtAddedViewModelDelegate = ThoughtAddedViewModelDelegate(
        upsertThoughtUseCase = upsertThoughtUseCase,
        resourceProvider = resourceProvider
    )

    private val effectProcessor: MutableSharedFlow<ViewEffect> = MutableSharedFlow(replay = 1)
    private val coroutineScope = TestScope

    val messageText = "Enter title and your thought"

    @BeforeEach
    fun setUp() = runTest {
        MviPlugins.initViewModelDelegateForTests(
            viewModelDelegate = thoughtAddedViewModelDelegate,
            eff = effectProcessor,
            scope = coroutineScope
        )

        whenever(resourceProvider.getString(R.string.snackbar_thought_not_saved)) doReturn messageText
    }

    @Test
    fun `should call upsertThoughtUseCase`() = runTest {
        // Given
        val title = "test title"
        val text = "test text"
        val event = ViewEvent.ThoughtAdded(
            title = title,
            text = text
        )

        // When
        thoughtAddedViewModelDelegate.onThoughtAdded(event)

        // Then
        verify(upsertThoughtUseCase).run(
            Thought(
                id = 0L,
                date = LocalDate.now(),
                title = event.title,
                text = event.text
            )
        )
    }

    @Test
    fun `should show snackBar when no title was entered`() = runTest {
        // Given
        val event = ViewEvent.ThoughtAdded(
            title = "",
            text = "test text"
        )

        // When
        thoughtAddedViewModelDelegate.onThoughtAdded(event)

        // Then
        val effect = effectProcessor.first { it is ViewEffect.ShowSnackBar }

        (effect as ViewEffect.ShowSnackBar).snackBarVisuals shouldBeEqualTo FeedbackData(
            message = messageText,
            actionLabel = null
        )
    }

    @Test
    fun `should show snackBar when no text was entered`() = runTest {
        // Given
        val event = ViewEvent.ThoughtAdded(
            title = "test title",
            text = ""
        )

        // When
        thoughtAddedViewModelDelegate.onThoughtAdded(event)

        // Then
        val effect = effectProcessor.first { it is ViewEffect.ShowSnackBar }

        (effect as ViewEffect.ShowSnackBar).snackBarVisuals shouldBeEqualTo FeedbackData(
            message = messageText,
            actionLabel = null
        )
    }

    @Test
    fun `should show snackBar when no title and text was entered`() = runTest {
        // Given
        val event = ViewEvent.ThoughtAdded(
            title = "",
            text = ""
        )

        // When
        thoughtAddedViewModelDelegate.onThoughtAdded(event)

        // Then
        val effect = effectProcessor.first { it is ViewEffect.ShowSnackBar }

        (effect as ViewEffect.ShowSnackBar).snackBarVisuals shouldBeEqualTo FeedbackData(
            message = messageText,
            actionLabel = null
        )
    }
}
