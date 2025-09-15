package com.mpauli.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.mpauli.base.util.Action
import com.mpauli.base.util.Actions.NoOp
import com.mpauli.core.ui.R
import com.mpauli.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    modifier: Modifier = Modifier,
    mainScreenEnum: MainScreenEnum,
    date: String? = null,
    showInfoButton: Boolean = false,
    onInfoButtonClick: Action? = null
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            when (mainScreenEnum) {
                MainScreenEnum.OverviewScreen -> Text(
                    stringResource(R.string.overview_screen_title)
                )

                MainScreenEnum.DayScreen -> Text(
                    stringResource(
                        R.string.day_screen_title,
                        date.orEmpty()
                    )
                )

                MainScreenEnum.PerspectiveScreen -> Text(
                    stringResource(R.string.perspective_screen_title)
                )
            }
        },
        actions = {
            if (mainScreenEnum == MainScreenEnum.PerspectiveScreen && showInfoButton) {
                onInfoButtonClick?.let {
                    AppIconButton(
                        isActive = false,
                        iconSize = 32.dp,
                        iconImage = Icons.Filled.Info,
                        onButtonClick = it
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AppTopAppBarOverviewPreview(
    @PreviewParameter(PreviewDataProvider::class) previewData: PreviewData
) {
    AppTheme {
        AppTopAppBar(
            mainScreenEnum = previewData.screen,
            date = previewData.date,
            showInfoButton = previewData.showInfoButton,
            onInfoButtonClick = previewData.onInfoButtonClick
        )
    }
}

private data class PreviewData(
    val date: String?,
    val screen: MainScreenEnum,
    val showInfoButton: Boolean,
    val onInfoButtonClick: Action?
)

private class PreviewDataProvider : PreviewParameterProvider<PreviewData> {
    override val values: Sequence<PreviewData> = sequenceOf(
        PreviewData(
            date = null,
            screen = MainScreenEnum.OverviewScreen,
            showInfoButton = false,
            onInfoButtonClick = null
        ),
        PreviewData(
            date = "05.08.2025",
            screen = MainScreenEnum.DayScreen,
            showInfoButton = false,
            onInfoButtonClick = null
        ),
        PreviewData(
            date = null,
            screen = MainScreenEnum.PerspectiveScreen,
            showInfoButton = true,
            onInfoButtonClick = NoOp
        )
    )
}
