package com.mpauli.feature.overviewscreen.viewmodel.model

import com.mpauli.feature.overviewscreen.domain.model.ThoughtItemState

internal sealed class ViewAction {

    data class UpdateThoughts(val thoughtsList: List<ThoughtItemState>) : ViewAction()
}
