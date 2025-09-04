package com.mpauli.feature.perspectivescreen.viewmodel

import com.mpauli.feature.base.mvi.MviViewModel
import com.mpauli.feature.perspectivescreen.viewmodel.delegates.ApodUpdateViewModelDelegate
import com.mpauli.feature.perspectivescreen.viewmodel.model.ViewAction
import com.mpauli.feature.perspectivescreen.viewmodel.model.ViewEffect
import com.mpauli.feature.perspectivescreen.viewmodel.model.ViewEvent
import com.mpauli.feature.perspectivescreen.viewmodel.model.ViewState
import kotlinx.coroutines.CoroutineDispatcher
import timber.log.Timber

internal class PerspectiveScreenViewModel(
    apodUpdateViewModelDelegate: ApodUpdateViewModelDelegate,
    stateProcessor: PerspectiveScreenStateProcessor,
    processorDispatcher: CoroutineDispatcher
) : MviViewModel<ViewState, ViewEvent, ViewAction, ViewEffect>(
    initialState = ViewState.DEFAULT,
    delegates = listOf(
        apodUpdateViewModelDelegate
    ),
    stateProcessor = stateProcessor,
    processorDispatcher = processorDispatcher
) {

    init {
        Timber.d("Initial update of the PerspectiveScreen")

        onEvent(ViewEvent.ApodUpdateTriggered)
    }
}
