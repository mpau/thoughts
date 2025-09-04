package com.mpauli.feature.overviewscreen.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mpauli.base.util.Action
import com.mpauli.base.util.Procedure
import com.mpauli.core.navigation.NavRoute
import com.mpauli.feature.overviewscreen.ui.OverviewScreen
import java.time.LocalDate

fun NavGraphBuilder.overviewScreenGraph(
    onNavigateToDayScreen: Procedure<LocalDate>,
    onNavigateToPerspectiveScreen: Action
) {
    composable(route = NavRoute.OverviewScreen.route) {
        OverviewScreen(
            onNavigateToDayScreen = { date ->
                onNavigateToDayScreen(date)
            },
            onNavigateToPerspectiveScreen = onNavigateToPerspectiveScreen
        )
    }
}
