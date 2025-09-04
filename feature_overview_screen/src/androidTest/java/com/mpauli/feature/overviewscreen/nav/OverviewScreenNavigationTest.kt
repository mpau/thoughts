package com.mpauli.feature.overviewscreen.nav

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
import com.mpauli.feature.overviewscreen.overviewScreenModule
import com.mpauli.feature.overviewscreen.ui.OverviewScreenTestTag
import org.junit.Rule
import org.junit.Test

internal class OverviewScreenNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val koinTestRule = KoinTestRule(
        modules = listOf(
            appModule,
            baseModule,
            overviewScreenModule
        )
    )

    @Test
    fun should_show_OverviewScreen() {
        // Given
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        // When
        composeTestRule.setContent {
            NavHost(
                navController = navController,
                startDestination = NavRoute.OverviewScreen.route
            ) {
                overviewScreenGraph(
                    onNavigateToDayScreen = {},
                    onNavigateToPerspectiveScreen = NoOp
                )
            }
        }

        // Then
        composeTestRule.onNodeWithTag(OverviewScreenTestTag.OverviewScreen.name).assertIsDisplayed()
    }
}
