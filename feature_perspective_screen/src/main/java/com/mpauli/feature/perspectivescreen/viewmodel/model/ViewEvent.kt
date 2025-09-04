package com.mpauli.feature.perspectivescreen.viewmodel.model

internal sealed class ViewEvent {

    data object ApodUpdateTriggered : ViewEvent()
}
