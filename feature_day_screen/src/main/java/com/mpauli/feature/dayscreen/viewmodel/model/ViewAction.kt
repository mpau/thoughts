package com.mpauli.feature.dayscreen.viewmodel.model

import com.mpauli.feature.dayscreen.domain.model.ThoughtItemState

internal sealed class ViewAction {

    data class UpdateThoughts(val thoughtsList: List<ThoughtItemState>) : ViewAction()
}
