package com.mpauli.feature.thoughtviewer.viewmodel.delegates

import com.mpauli.core.app.thoughts.domain.usecase.GetThoughtUseCase
import com.mpauli.core.models.Thought
import com.mpauli.core.test.TestScope
import com.mpauli.feature.base.mvi.MviActionProcessor
import com.mpauli.feature.base.mvi.MviPlugins
import com.mpauli.feature.thoughtviewer.domain.model.toItemState
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewAction
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEvent
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

internal class ThoughtUpdateViewModelDelegateTest {

    private val getThoughtUseCase: GetThoughtUseCase = mock()
    private val thoughtUpdateViewModelDelegate = ThoughtUpdateViewModelDelegate(
        getThoughtUseCase = getThoughtUseCase
    )

    private val actionProcessor: MviActionProcessor<ViewAction> = mock()
    private val coroutineScope = TestScope

    private val id = 1L
    private val thought = Thought(
        id = id,
        date = LocalDate.now(),
        title = "test title 1",
        text = "test text 1"
    )

    @BeforeEach
    fun setUp() {
        MviPlugins.initViewModelDelegateForTests(
            viewModelDelegate = thoughtUpdateViewModelDelegate,
            ap = actionProcessor,
            scope = coroutineScope
        )
    }

    @Test
    fun `should handle ViewEvent#ThoughtUpdateTriggered event`() {
        // Given
        whenever(getThoughtUseCase.run(id)) doReturn flowOf(thought)

        // When
        thoughtUpdateViewModelDelegate.onThoughtUpdateTriggered(ViewEvent.ThoughtUpdateTriggered(id))

        // Then
        verify(actionProcessor).process(ViewAction.UpdateThought(thought.toItemState()))
    }

    @Test
    fun `should call getThoughtUseCase`() {
        // Given
        whenever(getThoughtUseCase.run(id)) doReturn flowOf(thought)

        // When
        thoughtUpdateViewModelDelegate.onThoughtUpdateTriggered(ViewEvent.ThoughtUpdateTriggered(id))

        // Then
        @Suppress("UnusedFlow")
        verify(getThoughtUseCase).run(id)
    }
}
