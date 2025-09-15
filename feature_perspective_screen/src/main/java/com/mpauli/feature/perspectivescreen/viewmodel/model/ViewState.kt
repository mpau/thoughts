package com.mpauli.feature.perspectivescreen.viewmodel.model

import com.mpauli.feature.perspectivescreen.domain.model.ApodItemState
import java.time.LocalDate

internal data class ViewState(
    val apodItemState: ApodItemState,
    val isLoading: Boolean,
    val isError: Boolean
) {

    companion object {

        val DEFAULT = ViewState(
            apodItemState = ApodItemState(
                copyright = "",
                date = LocalDate.now(),
                explanation = "",
                hdUrl = "",
                title = "",
                url = ""
            ),
            isLoading = true,
            isError = false
        )
    }
}
