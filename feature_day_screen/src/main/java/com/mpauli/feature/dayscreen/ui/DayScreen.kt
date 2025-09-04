package com.mpauli.feature.dayscreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.mpauli.core.ui.components.AppBottomAppBar
import com.mpauli.core.ui.components.AppNoThoughtsHint
import com.mpauli.core.ui.components.AppThoughtCard
import com.mpauli.core.ui.components.AppTopAppBar
import com.mpauli.core.ui.components.MainScreenEnum
import com.mpauli.core.ui.theme.AppTheme
import com.mpauli.core.ui.util.add
import com.mpauli.feature.dayscreen.domain.model.ThoughtItemState
import com.mpauli.feature.dayscreen.viewmodel.DayScreenViewModel
import com.mpauli.feature.dayscreen.viewmodel.model.ViewEvent
import org.koin.compose.koinInject
import java.time.LocalDate

@Composable
internal fun DayScreen(
    dayScreenViewModel: DayScreenViewModel = koinInject(),
    date: LocalDate,
    onNavigateToThoughtViewer: Procedure<Long>,
    onNavigateToOverviewScreen: Action,
    onNavigateToPerspectiveScreen: Action
) {
    val items = dayScreenViewModel.state.collectAsState().value

    LaunchedEffect(date) {
        dayScreenViewModel.onEvent(ViewEvent.ThoughtsUpdateTriggered(date))
    }

    DayScreenStateless(
        modifier = Modifier.testTag(DayScreenTestTag.DayScreen.name),
        items = items.thoughts,
        date = date,
        onNavigateToThoughtViewer = onNavigateToThoughtViewer,
        onOverviewScreenButtonClick = { onNavigateToOverviewScreen.invoke() },
        onPerspectiveScreenButtonClick = { onNavigateToPerspectiveScreen.invoke() })
}

@Composable
private fun DayScreenStateless(
    modifier: Modifier = Modifier,
    items: List<ThoughtItemState>,
    date: LocalDate,
    onNavigateToThoughtViewer: Procedure<Long>,
    onOverviewScreenButtonClick: Action,
    onPerspectiveScreenButtonClick: Action
) {
    val dayMainScreenEnum = MainScreenEnum.DayScreen

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            AppTopAppBar(
                mainScreenEnum = dayMainScreenEnum,
                date = date.toStringWithFormat()
            )
        },
        bottomBar = {
            AppBottomAppBar(
                mainScreenEnum = dayMainScreenEnum,
                onNavigateToOverviewScreen = onOverviewScreenButtonClick,
                onNavigateToDayScreen = NoOp,
                onNavigateToPerspectiveScreen = onPerspectiveScreenButtonClick
            )
        }
    ) { innerPadding ->
        if (items.isEmpty()) {
            AppNoThoughtsHint(
                innerPadding = innerPadding,
                mainScreenEnum = dayMainScreenEnum
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
                items(
                    items = items,
                    key = { it.id }
                ) { item ->
                    AppThoughtCard(
                        text = item.title,
                        onCardClick = { onNavigateToThoughtViewer.invoke(item.id) })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DayScreenPreview(
    @PreviewParameter(PreviewDataProvider::class) previewData: PreviewData
) {
    AppTheme {
        DayScreenStateless(
            items = previewData.thoughtsList,
            date = LocalDate.now(),
            onOverviewScreenButtonClick = NoOp,
            onNavigateToThoughtViewer = {},
            onPerspectiveScreenButtonClick = NoOp
        )
    }
}

private data class PreviewData(val thoughtsList: List<ThoughtItemState>)

private class PreviewDataProvider : PreviewParameterProvider<PreviewData> {
    override val values: Sequence<PreviewData> = sequenceOf(
        PreviewData(thoughtsList),
        PreviewData(emptyList())
    )
}

private val thoughtsList = listOf(
    ThoughtItemState(
        id = 1L,
        title = "Very long first title: Blah, blah, blah, blah, blah, blah, blah, blah!",
        text = "First thought"
    ), ThoughtItemState(
        id = 2L,
        title = "Second title", text = "Second thought"
    ), ThoughtItemState(
        id = 3L,
        title = "Third title", text = "Third thought"
    ), ThoughtItemState(
        id = 4L,
        title = "Fourth title", text = "Fourth thought"
    ), ThoughtItemState(
        id = 5L,
        title = "Fives title", text = "Fives thought"
    )
)

enum class DayScreenTestTag {
    DayScreen
}
