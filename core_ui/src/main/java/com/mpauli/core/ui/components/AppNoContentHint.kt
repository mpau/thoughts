package com.mpauli.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mpauli.core.ui.R
import com.mpauli.core.ui.theme.AppTheme
import com.mpauli.core.ui.util.add

@Composable
fun AppNoContentHint(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    mainScreenEnum: MainScreenEnum
) {
    val noThoughtsText: Int? = when (mainScreenEnum) {
        MainScreenEnum.DayScreen -> R.string.no_day_thoughts_hint
        MainScreenEnum.OverviewScreen -> R.string.no_thoughts_hint
        MainScreenEnum.PerspectiveScreen -> R.string.no_perspective_hint
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                innerPadding.add(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        noThoughtsText?.let {
            Text(
                text = stringResource(it),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppNoThoughtsHintPreview() {
    AppTheme {
        AppNoContentHint(
            innerPadding = PaddingValues(),
            mainScreenEnum = MainScreenEnum.OverviewScreen
        )
    }
}
