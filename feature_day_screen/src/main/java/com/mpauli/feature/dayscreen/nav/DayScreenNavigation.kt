package com.mpauli.feature.dayscreen.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mpauli.base.util.Action
import com.mpauli.base.util.Procedure
import com.mpauli.core.navigation.NavRoute
import com.mpauli.feature.dayscreen.ui.DayScreen
import java.time.LocalDate

fun NavGraphBuilder.dayScreenGraph(
    onNavigateToOverviewScreen: Action,
    onNavigateToThoughtViewer: Procedure<Long>,
    onNavigateToPerspectiveScreen: Action
) {
    composable(
        route = NavRoute.DayScreen.route,
        arguments = listOf(
            navArgument(NavRoute.DayScreen.ARG_DATE) { type = LocalDateNavType }
        )
    ) { backStackEntry ->
        val date: LocalDate = backStackEntry.arguments
            ?.getString(NavRoute.DayScreen.ARG_DATE)
            ?.let { LocalDate.parse(it) }!!

        DayScreen(
            date = date,
            onNavigateToThoughtViewer = onNavigateToThoughtViewer,
            onNavigateToOverviewScreen = onNavigateToOverviewScreen,
            onNavigateToPerspectiveScreen = onNavigateToPerspectiveScreen
        )
    }
}
