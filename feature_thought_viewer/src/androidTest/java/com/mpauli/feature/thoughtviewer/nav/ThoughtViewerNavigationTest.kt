package com.mpauli.feature.thoughtviewer.nav

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
import com.mpauli.core.app.thoughts.domain.usecase.GetThoughtUseCase
import com.mpauli.core.models.Thought
import com.mpauli.core.navigation.NavRoute
import com.mpauli.core.test.KoinTestRule
import com.mpauli.feature.thoughtviewer.thoughtViewerModule
import com.mpauli.feature.thoughtviewer.ui.ThoughtViewerScreenTestTag
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import java.time.LocalDate

internal class ThoughtViewerNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val koinTestRule = KoinTestRule(
        modules = listOf(
            appModule,
            baseModule,
            thoughtViewerModule
        )
    )

    @Test
    fun should_show_ThoughtViewer() {
        // Given
        val id = 1L
        val getThoughtUseCase = mockk<GetThoughtUseCase>()
        coEvery { getThoughtUseCase.run(id) } returns flowOf(
            Thought(
                id = id,
                date = LocalDate.now(),
                title = "Test title",
                text = "Test text"
            )
        )
        loadKoinModules(module { single<GetThoughtUseCase> { getThoughtUseCase } })

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        // When
        composeTestRule.setContent {
            NavHost(
                navController = navController,
                startDestination = NavRoute.ThoughtViewer.createRoute(1L)
            ) {
                thoughtViewerGraph(
                    onClose = NoOp
                )
            }
        }

        // Then
        composeTestRule.onNodeWithTag(ThoughtViewerScreenTestTag.ThoughtViewerScreen.name).assertIsDisplayed()
    }
}
