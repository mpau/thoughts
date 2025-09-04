package com.mpauli.feature.base.mvi

import com.mpauli.base.util.Procedure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class MviViewModelDelegate<E, A, EF> {

    internal lateinit var actionProcessor: MviActionProcessor<A>
    internal lateinit var internalEventPublisher: Procedure<E>
    internal lateinit var effectShareFlow: MutableSharedFlow<EF>

    internal lateinit var _lifecycleScope: CoroutineScope
    protected val lifecycleScope: CoroutineScope
        get() = _lifecycleScope

    abstract fun onRegister(viewModelEventManager: MviViewModelEventManager)

    fun process(action: A) {
        actionProcessor.process(action)
    }

    fun publishInternally(event: E) {
        internalEventPublisher(event)
    }

    fun sendEffect(effect: EF) {
        if (::effectShareFlow.isInitialized) {
            _lifecycleScope.launch { effectShareFlow.emit(effect) }
        }
    }
}
