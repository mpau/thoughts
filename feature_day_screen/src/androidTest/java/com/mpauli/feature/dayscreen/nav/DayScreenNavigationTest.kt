package com.mpauli.feature.dayscreen.nav

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.mpauli.base.baseModule
import com.mpauli.base.util.Actions.NoOp
import com.mpauli.core.app.appModule
import com.mpauli.core.navigation.NavRoute
import com.mpauli.core.test.KoinTestRule
import com.mpauli.feature.dayscreen.dayScreenModule
import com.mpauli.feature.dayscreen.ui.DayScreenTestTag
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

internal class DayScreenNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val koinTestRule = KoinTestRule(
        modules = listOf(
            appModule,
            baseModule,
            dayScreenModule
        )
    )

    @Test
    fun should_show_DayScreen() {
        // Given
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        // When
        composeTestRule.setContent {
            NavHost(
                navController = navController,
                startDestination = NavRoute.DayScreen.createRoute(LocalDate.now())
            ) {
                dayScreenGraph(
                    onNavigateToOverviewScreen = NoOp,
                    onNavigateToThoughtViewer = {},
                    onNavigateToPerspectiveScreen = NoOp
                )
            }
        }

        // Then
        composeTestRule.onNodeWithTag(DayScreenTestTag.DayScreen.name).assertIsDisplayed()
    }
}
