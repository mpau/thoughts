package com.mpauli.feature.perspectivescreen.viewmodel.model

import com.mpauli.feature.perspectivescreen.domain.model.ApodItemState

internal sealed class ViewAction {

    data class UpdateApod(val apod: ApodItemState) : ViewAction()
}
