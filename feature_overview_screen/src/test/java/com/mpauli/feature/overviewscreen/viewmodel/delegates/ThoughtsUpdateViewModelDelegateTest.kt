package com.mpauli.feature.overviewscreen.viewmodel.delegates

import com.mpauli.core.app.thoughts.domain.usecase.GetAllThoughtsUseCase
import com.mpauli.core.models.Thought
import com.mpauli.core.test.TestScope
import com.mpauli.feature.base.mvi.MviActionProcessor
import com.mpauli.feature.base.mvi.MviPlugins
import com.mpauli.feature.overviewscreen.domain.model.toItemState
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewAction
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewEffect
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

internal class ThoughtsUpdateViewModelDelegateTest {

    private val getAllThoughtsUseCase: GetAllThoughtsUseCase = mock()
    private val thoughtsUpdateViewModelDelegate = ThoughtsUpdateViewModelDelegate(getAllThoughtsUseCase)

    private val actionProcessor: MviActionProcessor<ViewAction> = mock()
    private val effectProcessor: MutableSharedFlow<ViewEffect> = MutableSharedFlow(replay = 1)
    private val coroutineScope = TestScope

    private val date = LocalDate.now()
    private val thoughtsList = listOf(
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

    @BeforeEach
    fun setUp() {
        MviPlugins.initViewModelDelegateForTests(
            viewModelDelegate = thoughtsUpdateViewModelDelegate,
            ap = actionProcessor,
            eff = effectProcessor,
            scope = coroutineScope
        )
    }

    @Test
    fun `should handle ViewEvent#ThoughtsUpdateTriggered event`() {
        // Given
        whenever(getAllThoughtsUseCase.run()) doReturn flowOf(thoughtsList)

        // When
        thoughtsUpdateViewModelDelegate.onThoughtsUpdateTriggered(ViewEvent.ThoughtsUpdateTriggered)

        // Then
        val thoughtsItemStateList = thoughtsList.map { it.toItemState() }
        verify(actionProcessor).process(ViewAction.UpdateThoughts(thoughtsItemStateList))
    }

    @Test
    fun `should call getAllThoughtsUseCase`() {
        // Given
        whenever(getAllThoughtsUseCase.run()) doReturn flowOf(thoughtsList)

        // When
        thoughtsUpdateViewModelDelegate.onThoughtsUpdateTriggered(ViewEvent.ThoughtsUpdateTriggered)

        // Then
        @Suppress("UnusedFlow")
        verify(getAllThoughtsUseCase).run()
    }

    @Test
    fun `should process empty thoughts list`() {
        // Given
        whenever(getAllThoughtsUseCase.run()) doReturn flowOf(emptyList())

        // When
        thoughtsUpdateViewModelDelegate.onThoughtsUpdateTriggered(ViewEvent.ThoughtsUpdateTriggered)

        // Then
        verify(actionProcessor).process(ViewAction.UpdateThoughts(emptyList()))
    }
}
