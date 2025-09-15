package com.mpauli.feature.perspectivescreen.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
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
import com.mpauli.base.util.Action
import com.mpauli.base.util.Actions.NoOp
import com.mpauli.base.util.Procedure
import com.mpauli.core.ui.components.AppBottomAppBar
import com.mpauli.core.ui.components.AppLoadingIndicator
import com.mpauli.core.ui.components.AppNetworkImage
import com.mpauli.core.ui.components.AppNoContentHint
import com.mpauli.core.ui.components.AppScaffoldSnackBar
import com.mpauli.core.ui.components.AppTopAppBar
import com.mpauli.core.ui.components.MainScreenEnum
import com.mpauli.core.ui.theme.AppTheme
import com.mpauli.feature.perspectivescreen.viewmodel.PerspectiveScreenViewModel
import com.mpauli.feature.perspectivescreen.viewmodel.model.ViewEffect
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.koinInject
import java.time.LocalDate

@Composable
internal fun PerspectiveScreen(
    perspectiveScreenViewModel: PerspectiveScreenViewModel = koinInject(),
    onNavigateToOverviewScreen: Action,
    onNavigateToDayScreen: Procedure<LocalDate>
) {
    val viewState = perspectiveScreenViewModel.state.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        perspectiveScreenViewModel.effect.collectLatest { effect ->
            when (effect) {
                is ViewEffect.ShowSnackBar -> snackBarHostState.showSnackbar(effect.snackBarVisuals)
            }
        }
    }

    PerspectiveScreenStateless(
        modifier = Modifier.testTag(PerspectiveScreenTestTag.PerspectiveScreen.name),
        isLoading = viewState.isLoading,
        isError = viewState.isError,
        imageUrl = viewState.apodItemState.hdUrl,
        title = viewState.apodItemState.title,
        explanation = viewState.apodItemState.explanation,
        copyright = viewState.apodItemState.copyright,
        snackBarHostState = snackBarHostState,
        onOverviewScreenButtonClick = { onNavigateToOverviewScreen.invoke() },
        onDayScreenButtonClick = { onNavigateToDayScreen.invoke(LocalDate.now()) }
    )
}

@Composable
private fun PerspectiveScreenStateless(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isError: Boolean,
    imageUrl: String,
    title: String,
    explanation: String,
    copyright: String,
    snackBarHostState: SnackbarHostState,
    onOverviewScreenButtonClick: Action,
    onDayScreenButtonClick: Action
) {
    var showSheet by rememberSaveable { mutableStateOf(false) }

    val perspectiveMainScreenEnum = MainScreenEnum.PerspectiveScreen

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            AppTopAppBar(
                mainScreenEnum = perspectiveMainScreenEnum,
                showInfoButton = !isError,
                onInfoButtonClick = { showSheet = true }
            )
        },
        bottomBar = {
            AppBottomAppBar(
                mainScreenEnum = perspectiveMainScreenEnum,
                onNavigateToOverviewScreen = onOverviewScreenButtonClick,
                onNavigateToDayScreen = onDayScreenButtonClick,
                onNavigateToPerspectiveScreen = NoOp
            )
        },
        snackbarHost = {
            AppScaffoldSnackBar(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        when {
            isLoading -> AppLoadingIndicator()
            isError -> AppNoContentHint(
                innerPadding = innerPadding,
                mainScreenEnum = perspectiveMainScreenEnum
            )

            else -> AppNetworkImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                imageUrl = imageUrl
            )
        }

        if (showSheet) {
            InfoModalBottomSheet(
                title = title,
                explanation = explanation,
                copyright = copyright,
                onDismiss = { showSheet = false }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PerspectiveScreenPreview() {
    AppTheme {
        PerspectiveScreenStateless(
            snackBarHostState = remember { SnackbarHostState() },
            isLoading = false,
            isError = false,
            imageUrl = "https://www.something.com",
            title = "",
            explanation = "",
            copyright = "",
            onOverviewScreenButtonClick = NoOp,
            onDayScreenButtonClick = NoOp
        )
    }
}

enum class PerspectiveScreenTestTag {
    PerspectiveScreen
}
