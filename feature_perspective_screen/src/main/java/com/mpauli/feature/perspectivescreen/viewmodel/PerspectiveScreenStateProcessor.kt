package com.mpauli.feature.perspectivescreen.viewmodel

import com.mpauli.feature.base.mvi.MviStateProcessor
import com.mpauli.feature.perspectivescreen.viewmodel.model.ViewAction
import com.mpauli.feature.perspectivescreen.viewmodel.model.ViewState

internal class PerspectiveScreenStateProcessor : MviStateProcessor<ViewState, ViewAction> {

    private var previousState = ViewState.DEFAULT

    override suspend fun process(action: ViewAction): ViewState {
        val newState = when (action) {
            is ViewAction.ShowLoading -> previousState.copy(
                apodItemState = ViewState.DEFAULT.apodItemState,
                isLoading = true,
                isError = false
            )

            is ViewAction.ShowError -> previousState.copy(
                apodItemState = ViewState.DEFAULT.apodItemState,
                isLoading = false,
                isError = true
            )

            is ViewAction.UpdateApod -> previousState.copy(
                apodItemState = action.apod,
                isLoading = false,
                isError = true
            )
        }

        previousState = newState
        return newState
    }
}
