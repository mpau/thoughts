package com.mpauli.feature.thoughtviewer.viewmodel.model

import com.mpauli.feature.thoughtviewer.domain.model.ThoughtItemState

internal sealed class ViewAction {

    data class UpdateThought(val thoughtItemState: ThoughtItemState) : ViewAction()

    data class UpdateEditState(val editState: Boolean) : ViewAction()
}
