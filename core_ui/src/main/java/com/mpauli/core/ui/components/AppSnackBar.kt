package com.mpauli.core.ui.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppScaffoldSnackBar(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState
) {
    SnackbarHost(
        modifier = modifier.padding(8.dp),
        hostState = hostState
    )
}

@Composable
fun BoxScope.AppBoxSnackBar(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState
) {
    SnackbarHost(
        modifier = modifier
            .align(Alignment.BottomCenter)
            .imePadding()
            .padding(8.dp),
        hostState = hostState
    )
}
