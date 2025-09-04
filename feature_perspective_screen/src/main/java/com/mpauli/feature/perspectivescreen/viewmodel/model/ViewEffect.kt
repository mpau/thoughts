package com.mpauli.feature.perspectivescreen.viewmodel.model

import androidx.compose.material3.SnackbarVisuals

internal sealed class ViewEffect {

    data class ShowSnackBar(val snackBarVisuals: SnackbarVisuals) : ViewEffect()
}
