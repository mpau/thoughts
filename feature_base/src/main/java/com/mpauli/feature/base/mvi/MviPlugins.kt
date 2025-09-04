package com.mpauli.feature.base.mvi

import com.mpauli.base.util.Procedure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow

object MviPlugins {

    fun <E, A, Eff> initViewModelDelegateForTests(
        viewModelDelegate: MviViewModelDelegate<E, A, Eff>,
        ap: MviActionProcessor<A>? = null,
        ep: Procedure<E>? = null,
        eff: MutableSharedFlow<Eff>? = null,
        scope: CoroutineScope? = null
    ) {
        if (ap != null) viewModelDelegate.actionProcessor = ap
        if (ep != null) viewModelDelegate.internalEventPublisher = ep
        if (scope != null) viewModelDelegate._lifecycleScope = scope
        if (eff != null) viewModelDelegate.effectShareFlow = eff
    }
}
