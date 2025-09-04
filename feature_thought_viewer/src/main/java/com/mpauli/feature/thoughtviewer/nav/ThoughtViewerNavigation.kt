package com.mpauli.feature.thoughtviewer.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mpauli.base.util.Action
import com.mpauli.core.navigation.NavRoute
import com.mpauli.feature.thoughtviewer.ui.ThoughtViewerScreen

fun NavGraphBuilder.thoughtViewerGraph(
    onClose: Action
) {
    composable(
        route = NavRoute.ThoughtViewer.route,
        arguments = listOf(
            navArgument(NavRoute.ThoughtViewer.ARG_ID) { type = NavType.LongType }
        )
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getLong(NavRoute.ThoughtViewer.ARG_ID)!!

        ThoughtViewerScreen(
            id = id,
            onClose = onClose
        )
    }
}
