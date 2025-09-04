package com.mpauli.feature.thoughtviewer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.mpauli.base.datetime.toStringWithFormat
import com.mpauli.base.util.Action
import com.mpauli.base.util.Actions.NoOp
import com.mpauli.base.util.Procedure
import com.mpauli.core.ui.components.AppScaffoldSnackBar
import com.mpauli.core.ui.components.AppThoughtEditor
import com.mpauli.core.ui.theme.AppTheme
import com.mpauli.feature.thoughtviewer.R
import com.mpauli.feature.thoughtviewer.domain.model.ThoughtItemState
import com.mpauli.feature.thoughtviewer.viewmodel.ThoughtViewerViewModel
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEffect
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEvent
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.koinInject
import java.time.LocalDate

@Composable
internal fun ThoughtViewerScreen(
    thoughtViewerViewModel: ThoughtViewerViewModel = koinInject(),
    id: Long,
    onClose: Action
) {
    val item = thoughtViewerViewModel.state.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(id) {
        thoughtViewerViewModel.onEvent(ViewEvent.ThoughtUpdateTriggered(id))
    }

    LaunchedEffect(Unit) {
        thoughtViewerViewModel.effect.collectLatest { effect ->
            when (effect) {
                is ViewEffect.ShowSnackBar -> snackBarHostState.showSnackbar(effect.snackBarVisuals)
                is ViewEffect.Close -> onClose.invoke()
            }
        }
    }

    ThoughtViewerScreenStateless(
        modifier = Modifier.testTag(ThoughtViewerScreenTestTag.ThoughtViewerScreen.name),
        item = item.thought,
        editable = item.editState,
        headerTitle = stringResource(
            R.string.thought_viewer_header_title,
            item.thought.date.toStringWithFormat()
        ),
        snackBarHostState = snackBarHostState,
        onSaveButtonClick = { thoughtItemState ->
            thoughtViewerViewModel.onEvent(ViewEvent.ThoughtUpserted(thoughtItemState))
        },
        onEditButtonClick = {
            thoughtViewerViewModel.onEvent(ViewEvent.EditButtonClicked(!item.editState))
        },
        onDeleteButtonClick = {
            thoughtViewerViewModel.onEvent(ViewEvent.ThoughtDeleted(item.thought))
        },
        onCloseButtonClick = onClose
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThoughtViewerScreenStateless(
    modifier: Modifier = Modifier,
    item: ThoughtItemState,
    editable: Boolean,
    headerTitle: String,
    snackBarHostState: SnackbarHostState,
    onSaveButtonClick: Procedure<ThoughtItemState>,
    onEditButtonClick: Action,
    onDeleteButtonClick: Procedure<ThoughtItemState>,
    onCloseButtonClick: Action
) {
    var title by rememberSaveable(item.id) { mutableStateOf(item.title) }
    var text by rememberSaveable(item.id) { mutableStateOf(item.text) }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            TopAppBar(
                title = { Text(headerTitle) },
                navigationIcon = {
                    IconButton(
                        onClick = { onCloseButtonClick.invoke() },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.ArrowBackIosNew,
                                contentDescription = stringResource(R.string.thought_viewer_back_button),
                            )
                        }
                    )
                }
            )
        },
        snackbarHost = {
            AppScaffoldSnackBar(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            AppThoughtEditor(
                editable = editable,
                title = title,
                text = text,
                onTitleChange = { title = it },
                onTextChange = { text = it },
                onSaveButtonClick = {
                    onSaveButtonClick.invoke(
                        item.copy(
                            title = title,
                            text = text
                        )
                    )
                },
                onEditButtonClick = onEditButtonClick,
                onDeleteButtonClick = { onDeleteButtonClick.invoke(item) }
            )
        }
    }
}

private class EditableStateProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(
        true,
        false
    )
}

@Preview(showBackground = true)
@Composable
private fun ThoughtViewerScreenPreview(
    @PreviewParameter(EditableStateProvider::class) editable: Boolean
) {
    AppTheme {
        ThoughtViewerScreenStateless(
            item = ThoughtItemState(
                id = 1L,
                date = LocalDate.now(),
                title = "Test title",
                text = "Test text"
            ),
            editable = editable,
            headerTitle = stringResource(R.string.thought_viewer_header_title) + " 09.08.2025",
            snackBarHostState = remember { SnackbarHostState() },
            onSaveButtonClick = { _ -> },
            onEditButtonClick = NoOp,
            onDeleteButtonClick = {},
            onCloseButtonClick = NoOp
        )
    }
}

enum class ThoughtViewerScreenTestTag {
    ThoughtViewerScreen
}
