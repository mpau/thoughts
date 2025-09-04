package com.mpauli.feature.dayscreen.viewmodel

import com.mpauli.feature.base.mvi.MviViewModel
import com.mpauli.feature.dayscreen.viewmodel.delegates.ThoughtsUpdateViewModelDelegate
import com.mpauli.feature.dayscreen.viewmodel.model.ViewAction
import com.mpauli.feature.dayscreen.viewmodel.model.ViewEffect
import com.mpauli.feature.dayscreen.viewmodel.model.ViewEvent
import com.mpauli.feature.dayscreen.viewmodel.model.ViewState
import kotlinx.coroutines.CoroutineDispatcher

internal class DayScreenViewModel(
    thoughtsUpdateViewModelDelegate: ThoughtsUpdateViewModelDelegate,
    stateProcessor: DayScreenStateProcessor,
    processorDispatcher: CoroutineDispatcher
) : MviViewModel<ViewState, ViewEvent, ViewAction, ViewEffect>(
    initialState = ViewState.DEFAULT,
    delegates = listOf(
        thoughtsUpdateViewModelDelegate
    ),
    stateProcessor = stateProcessor,
    processorDispatcher = processorDispatcher
)
