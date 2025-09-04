package com.mpauli.feature.dayscreen.viewmodel.model

import java.time.LocalDate

internal sealed class ViewEvent {

    data class ThoughtsUpdateTriggered(val date: LocalDate) : ViewEvent()
}
