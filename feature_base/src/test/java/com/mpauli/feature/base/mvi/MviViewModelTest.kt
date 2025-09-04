package com.mpauli.feature.base.mvi

import com.mpauli.core.test.InstantExecutorExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

data class TestState(val value: String)

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class)
internal class MviViewModelTest {

    private lateinit var viewModel: MviViewModel<TestState, TestEvent, TestAction, TestEffect>

    private class TestViewModelDelegate :
        MviViewModelDelegate<TestEvent, TestAction, TestEffect>() {

        override fun onRegister(viewModelEventManager: MviViewModelEventManager) = Unit
    }

    private sealed class TestEvent {

        data object EventOne : TestEvent()
    }

    private sealed class TestAction {

        data object ActionOne : TestAction()
    }

    private sealed class TestEffect {

        data object EffectOne : TestEffect()
    }

    private val delegate: MviViewModelDelegate<TestEvent, TestAction, TestEffect> = mock()

    private val stateProcessor: MviStateProcessor<TestState, TestAction> = mock()

    @BeforeEach
    fun setUp() {
        viewModel = MviViewModel(
            processorDispatcher = StandardTestDispatcher(),
            delegates = listOf(delegate),
            initialState = TestState("Initial"),
            stateProcessor = stateProcessor
        )
    }

    @Test
    fun `should set initial state when ViewModel is initialized`() = runTest {
        // Given
        val expectedState = TestState("Initial")

        // Then
        val actualState = viewModel.state.first()
        actualState shouldBeEqualTo expectedState
    }

    @Test
    fun `should emit new state when process is called with an action`() = runTest {
        // Given
        val action = TestAction.ActionOne
        val newState = TestState("NewState")
        whenever(stateProcessor.process(action)) doReturn newState

        // When
        viewModel.process(action)

        // Then
        viewModel.state.first() shouldBeEqualTo newState
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should emit event to events flow when onEvent is called`() = runTest {
        // Given
        val event = TestEvent.EventOne
        val eventsCollector = mutableListOf<TestEvent>()

        // Collect events in a separate coroutine scope
        val job = launch {
            viewModel.events.collect { eventsCollector.add(it) }
        }

        // When
        viewModel.onEvent(event)
        advanceUntilIdle()

        // Then
        eventsCollector shouldBeEqualTo listOf(event)

        // Clean up
        job.cancel()
    }

    @Test
    fun `should initialize and register delegate when registerDelegate is called`() = runTest {
        // When
        viewModel.onEvent(TestEvent.EventOne) // Trigger onEvent to see if delegate processes the action

        // Then
        verify(delegate).onRegister(any())
    }

    @Test
    fun `should process action when process is called`() = runTest {
        // Given
        val action = TestAction.ActionOne

        // When
        viewModel.process(action)

        // Then
        verify(stateProcessor).process(action)
    }

    @Test
    fun `should emit effect when delegate sends an effect`() = runTest {
        // Given
        val delegateWithEffect: MviViewModelDelegate<TestEvent, TestAction, TestEffect> =
            TestViewModelDelegate()
        val viewModel = MviViewModel(
            processorDispatcher = StandardTestDispatcher(),
            delegates = listOf(delegateWithEffect),
            initialState = TestState("Initial"),
            stateProcessor = stateProcessor
        )
        val effectCollector = mutableListOf<TestEffect>()

        val job = launch {
            viewModel.effect.collect {
                effectCollector.add(it)
            }
        }

        // When
        delay(100)
        delegateWithEffect.sendEffect(TestEffect.EffectOne)
        advanceUntilIdle()

        // Then
        effectCollector shouldBeEqualTo listOf(TestEffect.EffectOne)

        // Cleanup
        job.cancel()
    }
}
