package com.mpauli.feature.perspectivescreen.viewmodel.model

import com.mpauli.feature.perspectivescreen.domain.model.ApodItemState

internal sealed class ViewAction {

    data object ShowLoading : ViewAction()

    data object ShowError : ViewAction()

    data class UpdateApod(val apod: ApodItemState) : ViewAction()
}
