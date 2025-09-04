package com.mpauli.feature.overviewscreen.viewmodel

import com.mpauli.feature.base.mvi.MviViewModel
import com.mpauli.feature.overviewscreen.viewmodel.delegates.ThoughtAddedViewModelDelegate
import com.mpauli.feature.overviewscreen.viewmodel.delegates.ThoughtsUpdateViewModelDelegate
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewAction
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewEffect
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewEvent
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewState
import kotlinx.coroutines.CoroutineDispatcher
import timber.log.Timber

internal class OverviewScreenViewModel(
    thoughtsUpdateViewModelDelegate: ThoughtsUpdateViewModelDelegate,
    thoughtAddedViewModelDelegate: ThoughtAddedViewModelDelegate,
    stateProcessor: OverviewScreenStateProcessor,
    processorDispatcher: CoroutineDispatcher
) : MviViewModel<ViewState, ViewEvent, ViewAction, ViewEffect>(
    initialState = ViewState.DEFAULT,
    delegates = listOf(
        thoughtsUpdateViewModelDelegate,
        thoughtAddedViewModelDelegate
    ),
    stateProcessor = stateProcessor,
    processorDispatcher = processorDispatcher
) {

    init {
        Timber.d("Initial update of the OverviewScreen")

        onEvent(ViewEvent.ThoughtsUpdateTriggered)
    }
}
