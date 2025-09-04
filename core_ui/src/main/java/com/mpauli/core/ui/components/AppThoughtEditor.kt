package com.mpauli.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.mpauli.base.util.Action
import com.mpauli.base.util.Actions.NoOp
import com.mpauli.base.util.Procedure
import com.mpauli.core.ui.R
import com.mpauli.core.ui.theme.AppTheme

@Composable
fun AppThoughtEditor(
    modifier: Modifier = Modifier,
    editable: Boolean,
    title: String,
    text: String,
    onTitleChange: Procedure<String>,
    onTextChange: Procedure<String>,
    onSaveButtonClick: Action,
    onEditButtonClick: Action,
    onDeleteButtonClick: Action
) {
    val onModifyButtonClick = if (editable) onSaveButtonClick else onEditButtonClick
    val modifyButtonIcon = if (editable) Icons.Filled.Save else Icons.Filled.Edit

    val modifyButtonDescription = if (editable) {
        stringResource(R.string.new_thought_save)
    } else {
        stringResource(R.string.edit_thought_button)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            stringResource(R.string.new_thought_title),
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            value = title,
            onValueChange = onTitleChange,
            readOnly = !editable
        )

        Text(
            stringResource(R.string.new_thought_text),
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            value = text,
            onValueChange = onTextChange,
            readOnly = !editable
        )

        ButtonRow(
            editable = editable,
            modifyButtonIcon = modifyButtonIcon,
            modifyButtonDescription = modifyButtonDescription,
            onModifyButtonClick = onModifyButtonClick,
            onDeleteButtonClick = onDeleteButtonClick
        )
    }
}

@Composable
private fun ButtonRow(
    editable: Boolean,
    modifyButtonIcon: ImageVector,
    modifyButtonDescription: String,
    onModifyButtonClick: Action,
    onDeleteButtonClick: Action
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!editable) {
            IconButton(
                onClick = onDeleteButtonClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.delete_thought_button),
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        IconButton(
            onClick = onModifyButtonClick,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = modifyButtonIcon,
                contentDescription = modifyButtonDescription,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

private class EditableStateProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(
        true,
        false
    )
}

@Preview(showBackground = true)
@Composable
private fun AppThoughtEditorPreview(
    @PreviewParameter(EditableStateProvider::class) editable: Boolean
) {
    AppTheme {
        AppThoughtEditor(
            editable = editable,
            title = "Only a preview title",
            text = "Only a preview thought",
            onTitleChange = {},
            onTextChange = {},
            onSaveButtonClick = NoOp,
            onEditButtonClick = NoOp,
            onDeleteButtonClick = NoOp
        )
    }
}
