package com.mpauli.feature.dayscreen.viewmodel.model

import com.mpauli.feature.dayscreen.domain.model.ThoughtItemState

internal data class ViewState(val thoughts: List<ThoughtItemState>) {

    companion object {

        val DEFAULT = ViewState(emptyList())
    }
}
