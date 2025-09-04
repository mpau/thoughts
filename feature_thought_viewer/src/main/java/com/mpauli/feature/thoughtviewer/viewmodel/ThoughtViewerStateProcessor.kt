package com.mpauli.feature.thoughtviewer.viewmodel

import com.mpauli.feature.base.mvi.MviStateProcessor
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewAction
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewState

internal class ThoughtViewerStateProcessor : MviStateProcessor<ViewState, ViewAction> {

    private var previousState = ViewState.DEFAULT

    override suspend fun process(action: ViewAction): ViewState {
        val newState = when (action) {
            is ViewAction.UpdateThought -> previousState.copy(thought = action.thoughtItemState)
            is ViewAction.UpdateEditState -> previousState.copy(editState = action.editState)
        }

        previousState = newState
        return newState
    }
}
