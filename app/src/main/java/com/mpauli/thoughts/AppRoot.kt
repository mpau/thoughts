package com.mpauli.thoughts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mpauli.core.ui.theme.AppTheme
import com.mpauli.thoughts.nav.MainNavHost

@Composable
fun AppRoot(
    navController: NavHostController = rememberNavController()
) {
    AppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            color = MaterialTheme.colorScheme.background
        ) {
            MainNavHost(navController)
        }
    }
}
