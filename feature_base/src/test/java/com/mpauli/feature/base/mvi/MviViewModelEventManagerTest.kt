package com.mpauli.feature.base.mvi

import com.mpauli.base.util.Procedure
import com.mpauli.core.test.TestScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

@OptIn(ExperimentalCoroutinesApi::class)
internal class MviViewModelEventManagerTest {

    private lateinit var coroutineScope: TestScope
    private lateinit var eventManager: MviViewModelEventManager

    private sealed class TestEvent {

        data object EventOne : TestEvent()
        data object EventTwo : TestEvent()
    }

    @BeforeEach
    fun setUp() {
        coroutineScope = TestScope
        eventManager = MviViewModelEventManager(coroutineScope)
    }

    @AfterEach
    fun tearDown() {
        coroutineScope.cancel()
    }

    @Test
    fun `should add handler to handlers map`() {
        // Given
        val handler = mock<Procedure<TestEvent.EventOne>>()

        // When
        eventManager.addHandler(handler)

        // Then
        val handlers = eventManager.handlers[TestEvent.EventOne::class]
        handlers?.size shouldBeEqualTo 1
        handlers?.first() shouldBeEqualTo handler
    }

    @Test
    fun `should invoke handler when event is queued`() = runTest {
        // Given
        val handler = mock<Procedure<TestEvent.EventOne>>()
        eventManager.addHandler(handler)

        // When
        eventManager.queue(TestEvent.EventOne)

        // Then
        advanceUntilIdle()
        verify(times(1)) { handler.invoke(TestEvent.EventOne) }
    }

    @Test
    fun `should not invoke handler when event type does not match`() = runTest {
        // Given
        val handler = mock<Procedure<TestEvent.EventOne>>()
        eventManager.addHandler(handler)

        // When
        eventManager.queue(TestEvent.EventTwo)

        // Then
        advanceUntilIdle()
        verifyNoInteractions(handler)
    }

    @Test
    fun `should call multiple handlers for the same event`() = runTest {
        // Given
        val handler1 = mock<Procedure<TestEvent.EventOne>>()
        val handler2 = mock<Procedure<TestEvent.EventOne>>()
        eventManager.addHandler(handler1)
        eventManager.addHandler(handler2)

        // When
        eventManager.queue(TestEvent.EventOne)

        // Then
        advanceUntilIdle()
        verify(times(1)) { handler1.invoke(TestEvent.EventOne) }
        verify(times(1)) { handler2.invoke(TestEvent.EventOne) }
    }
}
