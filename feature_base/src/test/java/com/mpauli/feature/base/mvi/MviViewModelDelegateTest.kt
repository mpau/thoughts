package com.mpauli.feature.base.mvi

import com.mpauli.base.util.Procedure
import com.mpauli.core.test.TestScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
internal class MviViewModelDelegateTest {

    private val actionProcessor: MviActionProcessor<ActionEvent.ActionTest> = mock()
    private val internalEventPublisher: Procedure<ViewEvent.EventTest> = mock()

    private class TestViewModelDelegate(
        actionProcessor: MviActionProcessor<ActionEvent.ActionTest>,
        internalEventPublisher: Procedure<ViewEvent.EventTest>,
        lifecycleScope: TestScope
    ) : MviViewModelDelegate<ViewEvent.EventTest, ActionEvent.ActionTest, TestEffect>() {

        init {
            this.actionProcessor = actionProcessor
            this.internalEventPublisher = internalEventPublisher
            this._lifecycleScope = lifecycleScope
        }

        override fun onRegister(viewModelEventManager: MviViewModelEventManager) = Unit
    }

    private sealed class ViewEvent {

        data class EventTest(val event: Int) : ViewEvent()
    }

    private sealed class ActionEvent {

        data class ActionTest(val action: Int) : ActionEvent()
    }

    private sealed class TestEffect {

        data object EffectOne : TestEffect()
    }

    private lateinit var viewModelDelegate: TestViewModelDelegate

    @BeforeEach
    fun setUp() {
        viewModelDelegate = TestViewModelDelegate(
            actionProcessor = actionProcessor,
            internalEventPublisher = internalEventPublisher,
            lifecycleScope = TestScope
        )
    }

    @Test
    fun `should call actionProcessor with action`() {
        // Given
        val action = ActionEvent.ActionTest(10)

        // When
        viewModelDelegate.process(action)

        // Then
        verify(actionProcessor).process(action)
    }

    @Test
    fun `should call internalEventPublisher with event`() {
        // Given
        val event = ViewEvent.EventTest(9)

        // When
        viewModelDelegate.publishInternally(event)

        // Then
        verify(internalEventPublisher).invoke(event)
    }

    @Test
    fun `should emit effect when effectShareFlow is initialized`() = runTest {
        // Given
        val effectCollector = mutableListOf<TestEffect>()
        val effectFlow = MutableSharedFlow<TestEffect>(replay = 1)
        viewModelDelegate.effectShareFlow = effectFlow

        // When
        val job = launch {
            viewModelDelegate.effectShareFlow.collect { effectCollector.add(it) }
        }
        viewModelDelegate.sendEffect(TestEffect.EffectOne)
        advanceUntilIdle()

        // Then
        effectCollector shouldBeEqualTo listOf(TestEffect.EffectOne)

        // Cleanup
        job.cancel()
    }

    @Test
    fun `should do nothing when effectShareFlow is not initialized`() {
        viewModelDelegate.sendEffect(TestEffect.EffectOne) // Should not throw an exception
    }

    @Test
    fun `should not emit effect if effectShareFlow is not initialized`() = runTest {
        // Given
        val effectCollector = mutableListOf<TestEffect>()
        val effectFlow = MutableSharedFlow<TestEffect>(replay = 1)
        viewModelDelegate.effectShareFlow = effectFlow

        // When
        val job = launch {
            viewModelDelegate.effectShareFlow.collect { effectCollector.add(it) }
        }
        advanceUntilIdle()

        // Then
        effectCollector shouldBeEqualTo emptyList()

        // Cleanup
        job.cancel()
    }
}
