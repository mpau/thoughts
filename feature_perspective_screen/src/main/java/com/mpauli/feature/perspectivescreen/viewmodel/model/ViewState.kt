package com.mpauli.feature.perspectivescreen.viewmodel.model

import com.mpauli.feature.perspectivescreen.domain.model.ApodItemState
import java.time.LocalDate

internal data class ViewState(val apodItemState: ApodItemState) {

    companion object {

        val DEFAULT = ViewState(
            ApodItemState(
                copyright = "",
                date = LocalDate.now(),
                explanation = "",
                hdUrl = "",
                title = "",
                url = ""
            )
        )
    }
}
