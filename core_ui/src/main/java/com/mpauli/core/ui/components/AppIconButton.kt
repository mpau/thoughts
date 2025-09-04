package com.mpauli.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mpauli.base.util.Action
import com.mpauli.base.util.Actions.NoOp
import com.mpauli.core.ui.theme.AppTheme

@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    iconSize: Dp,
    iconImage: ImageVector,
    text: String? = null,
    contentDescriptionText: String? = text,
    onButtonClick: Action
) {
    val backgroundColor = when (isActive) {
        true -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        false -> Color.Transparent
    }
    val iconTint = when (isActive) {
        true -> MaterialTheme.colorScheme.primary
        false -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    val textColor = when (isActive) {
        true -> MaterialTheme.colorScheme.primary
        false -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Surface(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onButtonClick)
            .semantics { contentDescriptionText?.let { contentDescription = it } }
            .padding(4.dp),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = iconImage,
                contentDescription = null,
                tint = iconTint
            )

            text?.let {
                Spacer(modifier = Modifier.height(1.dp))

                Text(
                    text = text,
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor
                )
            }
        }
    }
}

private class ActiveStateProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(
        true,
        false
    )
}

@Preview(showBackground = true)
@Composable
private fun AppIconButtonPreview(
    @PreviewParameter(ActiveStateProvider::class) isActive: Boolean
) {
    AppTheme {
        AppIconButton(
            isActive = isActive,
            iconSize = 34.dp,
            iconImage = Icons.Filled.CalendarMonth,
            text = "Month",
            onButtonClick = NoOp
        )
    }
}
