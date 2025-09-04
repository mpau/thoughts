package com.mpauli.feature.overviewscreen.viewmodel.model

internal sealed class ViewEvent {

    data object ThoughtsUpdateTriggered : ViewEvent()

    data class ThoughtAdded(
        val title: String,
        val text: String
    ) : ViewEvent()
}
