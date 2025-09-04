package com.mpauli.thoughts.nav

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mpauli.core.navigation.NavRoute
import com.mpauli.thoughts.AppRoot
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

internal class MainNavHostTest {

    private val date = LocalDate.parse("2025-08-11")

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @get:Rule
    val activityRule = ActivityScenarioRule(ComponentActivity::class.java)

    private lateinit var testController: TestNavHostController

    @Before
    fun setUp() {
        activityRule.scenario.onActivity { host ->
            host.setContent {
                testController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                AppRoot(testController)
            }
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun startDestination_is_Overview() {
        composeTestRule.runOnIdle {
            // Then
            testController.currentDestination?.route shouldBeEqualTo NavRoute.OverviewScreen.route
        }
    }

    @Test
    fun should_navigate_to_OverviewScreen() {
        composeTestRule.runOnIdle {
            // When
            testController.navigate(NavRoute.DayScreen.createRoute(date))
            testController.navigate(NavRoute.OverviewScreen.route)

            // Then
            testController.currentDestination?.route shouldBeEqualTo NavRoute.OverviewScreen.route
        }
    }

    @Test
    fun should_navigate_to_DayScreen() {
        composeTestRule.runOnIdle {
            // When
            testController.navigate(NavRoute.DayScreen.createRoute(date))

            // Then
            testController.currentDestination?.route shouldBeEqualTo NavRoute.DayScreen.route
        }
    }

    @Test
    fun should_navigate_to_thoughtViewer() {
        composeTestRule.runOnIdle {
            // Given
            val id = 1L

            // When
            testController.navigate(NavRoute.ThoughtViewer.createRoute(id))

            // Then
            testController.currentDestination?.route shouldBeEqualTo NavRoute.ThoughtViewer.route
        }
    }

    @Test
    fun should_navigate_to_PerspectiveScreen() {
        composeTestRule.runOnIdle {
            // When
            testController.navigate(NavRoute.PerspectiveScreen.route)

            // Then
            testController.currentDestination?.route shouldBeEqualTo NavRoute.PerspectiveScreen.route
        }
    }
}
