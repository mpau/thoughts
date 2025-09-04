package com.mpauli.core.navigation

import java.time.LocalDate

sealed class NavRoute(val route: String) {

    data object OverviewScreen : NavRoute("overview_screen")

    data object DayScreen : NavRoute("day_screen/{date}") {

        const val ARG_DATE = "date"

        fun createRoute(date: LocalDate) = "day_screen/$date"
    }

    data object ThoughtViewer : NavRoute("thought_viewer/{id}") {

        const val ARG_ID = "id"

        fun createRoute(id: Long) = "thought_viewer/$id"
    }

    data object PerspectiveScreen : NavRoute("perspective_screen")
}
