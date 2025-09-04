package com.mpauli.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mpauli.base.util.Action
import com.mpauli.base.util.Actions.NoOp
import com.mpauli.base.util.Procedure

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppAddThoughtModalBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    onTitleChange: Procedure<String>,
    onTextChange: Procedure<String>,
    snackBarHostState: SnackbarHostState,
    onSaveButtonClick: Action,
    onDismiss: Action
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
            ) {
                AppThoughtEditor(
                    editable = true,
                    title = title,
                    text = text,
                    onTitleChange = onTitleChange,
                    onTextChange = onTextChange,
                    onSaveButtonClick = onSaveButtonClick,
                    onEditButtonClick = NoOp,
                    onDeleteButtonClick = NoOp
                )
            }

            AppBoxSnackBar(hostState = snackBarHostState)
        }
    }
}

// @Preview: Check out the AppThoughtEditor.kt preview
