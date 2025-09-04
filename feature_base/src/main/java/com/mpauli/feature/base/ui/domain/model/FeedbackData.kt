package com.mpauli.feature.base.ui.domain.model

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

data class FeedbackData(
    override val message: String,
    override val actionLabel: String?
) : SnackbarVisuals {

    override val duration: SnackbarDuration = SnackbarDuration.Short
    override val withDismissAction: Boolean = false
}
