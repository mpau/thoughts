package com.mpauli.feature.thoughtviewer.viewmodel.delegates

import com.mpauli.base.res.ResourceProvider
import com.mpauli.base.util.Procedure
import com.mpauli.core.app.thoughts.domain.usecase.UpsertThoughtUseCase
import com.mpauli.core.test.TestScope
import com.mpauli.feature.base.mvi.MviPlugins
import com.mpauli.feature.base.ui.domain.model.FeedbackData
import com.mpauli.feature.thoughtviewer.R
import com.mpauli.feature.thoughtviewer.domain.model.ThoughtItemState
import com.mpauli.feature.thoughtviewer.domain.model.toDomain
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEffect
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEvent
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

internal class ThoughtUpsertedViewModelDelegateTest {

    private val resourceProvider: ResourceProvider = mock()
    private val upsertThoughtUseCase: UpsertThoughtUseCase = mock()
    private val thoughtUpsertedViewModelDelegate = ThoughtUpsertedViewModelDelegate(
        upsertThoughtUseCase = upsertThoughtUseCase,
        resourceProvider = resourceProvider
    )

    private val internalPublisher: Procedure<ViewEvent> = mock()
    private val effectProcessor: MutableSharedFlow<ViewEffect> = MutableSharedFlow(replay = 1)
    private val coroutineScope = TestScope

    private val messageText = "Enter title and your thought"
    private val testThoughtItemState = ThoughtItemState(
        id = 1L,
        date = LocalDate.now(),
        title = "test title",
        text = "test text"
    )

    @BeforeEach
    fun setUp() = runTest {
        MviPlugins.initViewModelDelegateForTests(
            viewModelDelegate = thoughtUpsertedViewModelDelegate,
            ep = internalPublisher,
            eff = effectProcessor,
            scope = coroutineScope
        )

        whenever(resourceProvider.getString(R.string.snackbar_thought_not_updated)) doReturn messageText
    }

    @Test
    fun `should call upsertThoughtUseCase`() = runTest {
        // Given
        val event = ViewEvent.ThoughtUpserted(testThoughtItemState)

        // When
        thoughtUpsertedViewModelDelegate.onThoughtUpserted(event)

        // Then
        verify(upsertThoughtUseCase).run(testThoughtItemState.toDomain())
    }

    @Test
    fun `should publish internal ViewEvent#EditButtonClicked`() {
        // Given
        val event = ViewEvent.ThoughtUpserted(testThoughtItemState)

        // When
        thoughtUpsertedViewModelDelegate.onThoughtUpserted(event)

        // Then
        verify(internalPublisher).invoke(ViewEvent.EditButtonClicked(false))
    }

    @Test
    fun `should show snackBar when no title was entered`() = runTest {
        // Given
        val event = ViewEvent.ThoughtUpserted(testThoughtItemState.copy(title = ""))

        // When
        thoughtUpsertedViewModelDelegate.onThoughtUpserted(event)

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
        val event = ViewEvent.ThoughtUpserted(testThoughtItemState.copy(text = ""))

        // When
        thoughtUpsertedViewModelDelegate.onThoughtUpserted(event)

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
        val event = ViewEvent.ThoughtUpserted(
            testThoughtItemState.copy(
                title = "",
                text = ""
            )
        )

        // When
        thoughtUpsertedViewModelDelegate.onThoughtUpserted(event)

        // Then
        val effect = effectProcessor.first { it is ViewEffect.ShowSnackBar }

        (effect as ViewEffect.ShowSnackBar).snackBarVisuals shouldBeEqualTo FeedbackData(
            message = messageText,
            actionLabel = null
        )
    }
}
