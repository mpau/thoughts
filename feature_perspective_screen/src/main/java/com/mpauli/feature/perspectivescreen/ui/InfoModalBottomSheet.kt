package com.mpauli.feature.perspectivescreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mpauli.base.util.Action
import com.mpauli.feature.perspectivescreen.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InfoModalBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    explanation: String,
    copyright: String,
    onDismiss: Action
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    ModalBottomSheet(
        modifier = modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = explanation,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (copyright.isNotBlank()) {
                Text(
                    text = stringResource(
                        R.string.apod_copyright,
                        copyright
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
