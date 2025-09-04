package com.mpauli.feature.thoughtviewer.viewmodel.delegates

import com.mpauli.core.test.TestScope
import com.mpauli.feature.base.mvi.MviActionProcessor
import com.mpauli.feature.base.mvi.MviPlugins
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewAction
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEvent
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class EditButtonClickedViewModelDelegateTest {

    private val editButtonClickedViewModelDelegate = EditButtonClickedViewModelDelegate()

    private val actionProcessor: MviActionProcessor<ViewAction> = mock()
    private val coroutineScope = TestScope

    @BeforeEach
    fun setUp() {
        MviPlugins.initViewModelDelegateForTests(
            viewModelDelegate = editButtonClickedViewModelDelegate,
            ap = actionProcessor,
            scope = coroutineScope
        )
    }

    @Test
    fun `should handle ViewEvent#EditButtonClicked event`() {
        // Given
        val event = ViewEvent.EditButtonClicked(true)

        // When
        editButtonClickedViewModelDelegate.onEditButtonClicked(event)

        // Then
        verify(actionProcessor).process(ViewAction.UpdateEditState(event.editState))
    }
}
