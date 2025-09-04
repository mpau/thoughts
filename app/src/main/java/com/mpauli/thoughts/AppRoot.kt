package com.mpauli.thoughts

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mpauli.core.ui.theme.AppTheme
import com.mpauli.thoughts.nav.MainNavHost

@Composable
fun AppRoot(
    navController: NavHostController = rememberNavController()
) {
    AppTheme { MainNavHost(navController) }
}
