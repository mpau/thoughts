package com.mpauli.feature.dayscreen.viewmodel

import com.mpauli.feature.base.mvi.MviStateProcessor
import com.mpauli.feature.dayscreen.viewmodel.model.ViewAction
import com.mpauli.feature.dayscreen.viewmodel.model.ViewState

internal class DayScreenStateProcessor : MviStateProcessor<ViewState, ViewAction> {

    private var previousState = ViewState.DEFAULT

    override suspend fun process(action: ViewAction): ViewState {
        val newState = when (action) {
            is ViewAction.UpdateThoughts -> previousState.copy(thoughts = action.thoughtsList)
        }

        previousState = newState
        return newState
    }
}
