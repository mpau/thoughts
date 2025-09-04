package com.mpauli.feature.thoughtviewer.viewmodel.model

import com.mpauli.feature.thoughtviewer.domain.model.ThoughtItemState

internal sealed class ViewEvent {

    data class ThoughtUpdateTriggered(val id: Long) : ViewEvent()

    data class EditButtonClicked(val editState: Boolean) : ViewEvent()

    data class ThoughtUpserted(val thoughtItemState: ThoughtItemState) : ViewEvent()

    data class ThoughtDeleted(val thoughtItemState: ThoughtItemState) : ViewEvent()
}
