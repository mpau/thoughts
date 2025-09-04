package com.mpauli.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mpauli.base.util.Action
import com.mpauli.base.util.Actions.NoOp
import com.mpauli.core.ui.theme.AppTheme

@Composable
fun AppThoughtCard(
    modifier: Modifier = Modifier,
    text: String,
    onCardClick: Action
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onCardClick.invoke() },
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(
                width = 1.dp,
                color = Color.Black
            ),
            content = {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = text,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppThoughtCardPreview() {
    AppTheme {
        AppThoughtCard(
            text = "My test thought",
            onCardClick = NoOp
        )
    }
}
