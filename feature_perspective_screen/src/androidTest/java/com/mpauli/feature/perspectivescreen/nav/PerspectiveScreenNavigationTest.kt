package com.mpauli.feature.perspectivescreen.nav

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.mpauli.base.baseModule
import com.mpauli.base.util.Actions.NoOp
import com.mpauli.core.app.apod.domain.usecase.GetApodUseCase
import com.mpauli.core.app.appModule
import com.mpauli.core.models.Apod
import com.mpauli.core.navigation.NavRoute
import com.mpauli.core.test.KoinTestRule
import com.mpauli.feature.perspectivescreen.perspectiveScreenModule
import com.mpauli.feature.perspectivescreen.ui.PerspectiveScreenTestTag
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import java.time.LocalDate

internal class PerspectiveScreenNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val koinTestRule = KoinTestRule(
        modules = listOf(
            appModule,
            baseModule,
            perspectiveScreenModule
        )
    )

    @Test
    fun should_show_PerspectiveScreen() {
        // Given
        val getApodUseCase = mockk<GetApodUseCase>()
        coEvery { getApodUseCase.run() } returns Result.success(
            Apod(
                copyright = "Panther Observatory",
                date = LocalDate.now(),
                explanation = "In this stunning cosmic vista, galaxy M81 is...",
                hdUrl = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "Galaxy Wars: M81 versus M82",
                url = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c25.jpg"
            )
        )
        loadKoinModules(module { single<GetApodUseCase> { getApodUseCase } })

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        // When
        composeTestRule.setContent {
            NavHost(
                navController = navController,
                startDestination = NavRoute.PerspectiveScreen.route
            ) {
                perspectiveScreenGraph(
                    onNavigateToOverviewScreen = NoOp,
                    onNavigateToDayScreen = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithTag(PerspectiveScreenTestTag.PerspectiveScreen.name).assertIsDisplayed()
    }
}
