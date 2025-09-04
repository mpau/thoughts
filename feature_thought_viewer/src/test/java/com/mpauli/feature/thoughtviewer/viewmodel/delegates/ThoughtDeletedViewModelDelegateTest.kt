package com.mpauli.feature.thoughtviewer.viewmodel.delegates

import com.mpauli.core.app.thoughts.domain.usecase.DeleteThoughtUseCase
import com.mpauli.core.test.TestScope
import com.mpauli.feature.base.mvi.MviPlugins
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
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDate

internal class ThoughtDeletedViewModelDelegateTest {

    private val deleteThoughtUseCase: DeleteThoughtUseCase = mock()
    private val thoughtDeletedViewModelDelegate = ThoughtDeletedViewModelDelegate(
        deleteThoughtUseCase = deleteThoughtUseCase
    )

    private val effectProcessor: MutableSharedFlow<ViewEffect> = MutableSharedFlow(replay = 1)
    private val coroutineScope = TestScope

    private val testThoughtItemState = ThoughtItemState(
        id = 1L,
        date = LocalDate.now(),
        title = "test title",
        text = "test text"
    )

    @BeforeEach
    fun setUp() {
        MviPlugins.initViewModelDelegateForTests(
            viewModelDelegate = thoughtDeletedViewModelDelegate,
            eff = effectProcessor,
            scope = coroutineScope
        )
    }

    @Test
    fun `should call deleteThoughtUseCase`() = runTest {
        // Given
        val event = ViewEvent.ThoughtDeleted(testThoughtItemState)

        // When
        thoughtDeletedViewModelDelegate.onThoughtDeleted(event)

        // Then
        verify(deleteThoughtUseCase).run(testThoughtItemState.toDomain())
    }

    @Test
    fun `should send ViewEffect#Close`() = runTest {
        // Given
        val event = ViewEvent.ThoughtDeleted(testThoughtItemState)

        // When
        thoughtDeletedViewModelDelegate.onThoughtDeleted(event)

        // Then
        effectProcessor.first() shouldBeEqualTo ViewEffect.Close
    }
}
