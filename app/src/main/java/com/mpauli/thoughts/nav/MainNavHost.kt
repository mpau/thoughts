package com.mpauli.thoughts.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mpauli.core.navigation.NavRoute
import com.mpauli.feature.dayscreen.nav.dayScreenGraph
import com.mpauli.feature.overviewscreen.nav.overviewScreenGraph
import com.mpauli.feature.perspectivescreen.nav.perspectiveScreenGraph
import com.mpauli.feature.thoughtviewer.nav.thoughtViewerGraph

@Composable
internal fun MainNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController,
        startDestination = NavRoute.OverviewScreen.route
    ) {
        overviewScreenGraph(
            onNavigateToDayScreen = { date ->
                navController.navigate(NavRoute.DayScreen.createRoute(date))
            },
            onNavigateToPerspectiveScreen = {
                navController.navigate(NavRoute.PerspectiveScreen.route)
            }
        )

        dayScreenGraph(
            onNavigateToThoughtViewer = { id ->
                navController.navigate(NavRoute.ThoughtViewer.createRoute(id))
            },
            onNavigateToOverviewScreen = {
                navController.navigate(NavRoute.OverviewScreen.route)
            },
            onNavigateToPerspectiveScreen = {
                navController.navigate(NavRoute.PerspectiveScreen.route)
            }
        )

        thoughtViewerGraph(
            onClose = {
                navController.popBackStack()
            }
        )

        perspectiveScreenGraph(
            onNavigateToOverviewScreen = {
                navController.navigate(NavRoute.OverviewScreen.route)
            },
            onNavigateToDayScreen = { date ->
                navController.navigate(NavRoute.DayScreen.createRoute(date))
            }
        )
    }
}
