package com.mpauli.feature.overviewscreen.viewmodel.model

import com.mpauli.feature.overviewscreen.domain.model.ThoughtItemState

internal data class ViewState(val thoughts: List<ThoughtItemState>) {

    companion object {

        val DEFAULT = ViewState(emptyList())
    }
}
