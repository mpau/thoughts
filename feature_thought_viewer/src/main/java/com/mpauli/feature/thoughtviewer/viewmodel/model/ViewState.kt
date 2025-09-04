package com.mpauli.feature.thoughtviewer.viewmodel.model

import com.mpauli.feature.thoughtviewer.domain.model.ThoughtItemState
import java.time.LocalDate

internal data class ViewState(
    val thought: ThoughtItemState,
    val editState: Boolean
) {

    companion object {

        val DEFAULT = ViewState(
            thought = ThoughtItemState(
                id = 0L,
                date = LocalDate.now(),
                title = "",
                text = ""
            ),
            editState = false
        )
    }
}
