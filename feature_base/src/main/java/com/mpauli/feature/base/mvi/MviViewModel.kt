package com.mpauli.feature.base.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

open class MviViewModel<S, E, A, EF>(
    private val initialState: S,
    delegates: List<MviViewModelDelegate<E, A, EF>>,
    private val stateProcessor: MviStateProcessor<S, A>,
    processorDispatcher: CoroutineContext
) : ViewModel() {

    private val _state by lazy { MutableStateFlow(initialState) }
    val state = _state.asStateFlow()

    internal val events = MutableSharedFlow<E>(REPLAY_COUNT)
    private val mviViewModelEventManager = MviViewModelEventManager(viewModelScope)

    private val actions = MutableSharedFlow<A>(REPLAY_COUNT)
    private val mviActionProcessor = MviActionProcessor<A> { action ->
        Timber.d("Emitting action $action")

        val result = actions.tryEmit(action)
        Timber.d("Result from action $action = $result")
    }

    private val _effect by lazy { MutableSharedFlow<EF>() }
    val effect = _effect.asSharedFlow()

    init {
        Timber.d("Initial state $initialState")

        initObservers(processorDispatcher)

        delegates.forEach { registerDelegates(it) }
    }

    private fun initObservers(processorDispatcher: CoroutineContext) {
        viewModelScope.launch {
            launch(processorDispatcher) { observeEvents() }
            launch(processorDispatcher) { observeActions() }
        }
    }

    private suspend fun observeEvents() {
        events.collect { event ->
            try {
                handleEvent(event)
            } catch (throwable: Throwable) {
                Timber.e(
                    t = throwable,
                    message = "Could not handle event $event"
                )
            }
        }
    }

    private fun handleEvent(event: E) {
        Timber.d("Handle event $event")

        mviViewModelEventManager.queue(event as Any)
    }

    private suspend fun observeActions() {
        actions.collect { action ->
            try {
                Timber.d("Processing action $action")

                this@MviViewModel.process(action)
            } catch (throwable: Throwable) {
                Timber.e(
                    t = throwable,
                    message = "Could not process action $action"
                )
            }
        }
    }

    suspend fun process(action: A) {
        val newState = stateProcessor.process(action)
        Timber.d("New state $newState")

        _state.emit(newState)
    }

    private fun registerDelegates(delegate: MviViewModelDelegate<E, A, EF>) {
        Timber.d("Registering delegate $delegate")

        delegate.apply {
            actionProcessor = mviActionProcessor
            internalEventPublisher = { onEvent(it) }
            effectShareFlow = _effect
            _lifecycleScope = viewModelScope
            onRegister(mviViewModelEventManager)
        }
    }

    open fun onEvent(event: E) {
        Timber.d("onEvent $event")

        events.tryEmit(event)
    }

    companion object {

        private const val REPLAY_COUNT = 10
    }
}
