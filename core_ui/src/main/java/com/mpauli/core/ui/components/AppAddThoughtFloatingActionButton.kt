package com.mpauli.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mpauli.base.util.Action
import com.mpauli.base.util.Actions.NoOp
import com.mpauli.core.ui.R
import com.mpauli.core.ui.theme.AppTheme

@Composable
fun AppAddThoughtFloatingActionButton(
    modifier: Modifier = Modifier,
    onButtonClick: Action
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onButtonClick
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = stringResource(R.string.add_thought_button)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppAddThoughtFloatingActionButtonPreview() {
    AppTheme {
        AppAddThoughtFloatingActionButton(onButtonClick = NoOp)
    }
}
