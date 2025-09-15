package com.mpauli.feature.overviewscreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.mpauli.base.datetime.toStringWithFormat
import com.mpauli.base.util.Action
import com.mpauli.base.util.Actions.NoOp
import com.mpauli.base.util.Procedure
import com.mpauli.core.ui.components.AppAddThoughtFloatingActionButton
import com.mpauli.core.ui.components.AppAddThoughtModalBottomSheet
import com.mpauli.core.ui.components.AppBottomAppBar
import com.mpauli.core.ui.components.AppNoContentHint
import com.mpauli.core.ui.components.AppScaffoldSnackBar
import com.mpauli.core.ui.components.AppThoughtCard
import com.mpauli.core.ui.components.AppTopAppBar
import com.mpauli.core.ui.components.MainScreenEnum
import com.mpauli.core.ui.theme.AppTheme
import com.mpauli.core.ui.util.add
import com.mpauli.feature.overviewscreen.viewmodel.OverviewScreenViewModel
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewEffect
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewEvent
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.koinInject
import java.time.LocalDate

@Composable
internal fun OverviewScreen(
    overviewScreenViewModel: OverviewScreenViewModel = koinInject(),
    onNavigateToDayScreen: Procedure<LocalDate>,
    onNavigateToPerspectiveScreen: Action
) {
    val items = overviewScreenViewModel.state.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    val dateList = items.thoughts.map { it.date }.distinct()

    LaunchedEffect(Unit) {
        overviewScreenViewModel.effect.collectLatest { effect ->
            when (effect) {
                is ViewEffect.ShowSnackBar -> snackBarHostState.showSnackbar(effect.snackBarVisuals)
            }
        }
    }

    OverviewScreenStateless(
        modifier = Modifier.testTag(OverviewScreenTestTag.OverviewScreen.name),
        dateList = dateList,
        snackBarHostState = snackBarHostState,
        onNewThoughtAdded = { title, text ->
            overviewScreenViewModel.onEvent(
                ViewEvent.ThoughtAdded(
                    title = title,
                    text = text
                )
            )
        },
        onItemCardClick = onNavigateToDayScreen,
        onDayScreenButtonClick = { onNavigateToDayScreen.invoke(LocalDate.now()) },
        onPerspectiveScreenButtonClick = { onNavigateToPerspectiveScreen.invoke() }
    )
}

@Composable
private fun OverviewScreenStateless(
    modifier: Modifier = Modifier,
    dateList: List<LocalDate>,
    snackBarHostState: SnackbarHostState,
    onNewThoughtAdded: (String, String) -> Unit,
    onItemCardClick: Procedure<LocalDate>,
    onDayScreenButtonClick: Action,
    onPerspectiveScreenButtonClick: Action
) {
    var title by rememberSaveable { mutableStateOf("") }
    var text by rememberSaveable { mutableStateOf("") }
    var showSheet by rememberSaveable { mutableStateOf(false) }
    val overviewMainScreenEnum = MainScreenEnum.OverviewScreen

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            AppTopAppBar(mainScreenEnum = overviewMainScreenEnum)
        },
        bottomBar = {
            AppBottomAppBar(
                mainScreenEnum = overviewMainScreenEnum,
                onNavigateToOverviewScreen = NoOp,
                onNavigateToDayScreen = onDayScreenButtonClick,
                onNavigateToPerspectiveScreen = onPerspectiveScreenButtonClick
            )
        },
        snackbarHost = {
            if (!showSheet) AppScaffoldSnackBar(hostState = snackBarHostState)
        },
        floatingActionButton = {
            AppAddThoughtFloatingActionButton(onButtonClick = { showSheet = true })
        }
    ) { innerPadding ->
        if (dateList.isEmpty()) {
            AppNoContentHint(
                innerPadding = innerPadding,
                mainScreenEnum = overviewMainScreenEnum
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        innerPadding.add(
                            start = 8.dp,
                            end = 8.dp
                        )
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(dateList) { date ->
                    AppThoughtCard(
                        text = date.toStringWithFormat(),
                        onCardClick = { onItemCardClick.invoke(date) }
                    )
                }
            }
        }

        if (showSheet) {
            AppAddThoughtModalBottomSheet(
                title = title,
                text = text,
                onTitleChange = { title = it },
                onTextChange = { text = it },
                onDismiss = {
                    title = ""
                    text = ""
                    showSheet = false
                },
                onSaveButtonClick = {
                    onNewThoughtAdded.invoke(
                        title,
                        text
                    )

                    val isTitleAndTextFilledOut = title.isNotBlank() && text.isNotBlank()
                    if (isTitleAndTextFilledOut) {
                        title = ""
                        text = ""
                        showSheet = false
                    }
                },
                snackBarHostState = snackBarHostState
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OverviewScreenPreview(
    @PreviewParameter(PreviewDataProvider::class) previewData: PreviewData
) {
    AppTheme {
        OverviewScreenStateless(
            dateList = previewData.dateList,
            snackBarHostState = remember { SnackbarHostState() },
            onNewThoughtAdded = { _, _ -> },
            onItemCardClick = {},
            onDayScreenButtonClick = NoOp,
            onPerspectiveScreenButtonClick = NoOp
        )
    }
}

private class PreviewDataProvider : PreviewParameterProvider<PreviewData> {
    override val values: Sequence<PreviewData> = sequenceOf(
        PreviewData(dateList),
        PreviewData(emptyList())
    )
}

private data class PreviewData(val dateList: List<LocalDate>)

private val dateList = listOf(
    LocalDate.parse("2025-08-02"),
    LocalDate.parse("2025-08-02"),
    LocalDate.parse("2025-08-02"),
    LocalDate.parse("2025-08-03"),
    LocalDate.parse("2025-08-03")
)

enum class OverviewScreenTestTag {
    OverviewScreen
}
