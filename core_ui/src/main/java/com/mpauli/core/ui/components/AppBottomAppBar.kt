package com.mpauli.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarViewDay
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mpauli.base.util.Action
import com.mpauli.base.util.Actions.NoOp
import com.mpauli.core.ui.R
import com.mpauli.core.ui.theme.AppTheme

@Composable
fun AppBottomAppBar(
    modifier: Modifier = Modifier,
    mainScreenEnum: MainScreenEnum,
    onNavigateToOverviewScreen: Action,
    onNavigateToDayScreen: Action,
    onNavigateToPerspectiveScreen: Action,
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AppIconButton(
                isActive = mainScreenEnum == MainScreenEnum.OverviewScreen,
                iconSize = 34.dp,
                iconImage = Icons.Filled.CalendarMonth,
                text = stringResource(R.string.overview_screen_button),
                onButtonClick = onNavigateToOverviewScreen
            )

            AppIconButton(
                isActive = mainScreenEnum == MainScreenEnum.DayScreen,
                iconSize = 36.dp,
                iconImage = Icons.Filled.CalendarViewDay,
                text = stringResource(R.string.day_screen_button),
                onButtonClick = onNavigateToDayScreen
            )

            AppIconButton(
                isActive = mainScreenEnum == MainScreenEnum.PerspectiveScreen,
                iconSize = 36.dp,
                iconImage = Icons.Filled.Visibility,
                text = stringResource(R.string.perspective_screen_button),
                onButtonClick = onNavigateToPerspectiveScreen
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppBottomAppBarPreview() {
    AppTheme {
        AppBottomAppBar(
            mainScreenEnum = MainScreenEnum.OverviewScreen,
            onNavigateToOverviewScreen = NoOp,
            onNavigateToDayScreen = NoOp,
            onNavigateToPerspectiveScreen = NoOp
        )
    }
}
