package com.mpauli.feature.perspectivescreen.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mpauli.base.util.Action
import com.mpauli.base.util.Procedure
import com.mpauli.core.navigation.NavRoute
import com.mpauli.feature.perspectivescreen.ui.PerspectiveScreen
import java.time.LocalDate

fun NavGraphBuilder.perspectiveScreenGraph(
    onNavigateToOverviewScreen: Action,
    onNavigateToDayScreen: Procedure<LocalDate>
) {
    composable(route = NavRoute.PerspectiveScreen.route) {
        PerspectiveScreen(
            onNavigateToOverviewScreen = onNavigateToOverviewScreen,
            onNavigateToDayScreen = { date ->
                onNavigateToDayScreen(date)
            }
        )
    }
}
