package com.pichurchyk.fitflow.ui.screen.addintake

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.ui.ext.getTitle
import com.pichurchyk.fitflow.ui.ext.getUnit
import com.pichurchyk.fitflow.ui.theme.TextStyles
import com.pichurchyk.nutrition.database.model.IntakeType

@Composable
fun IntakeInput(
    modifier: Modifier = Modifier,
    intakeType: IntakeType,
    value: Int,
    needBottomRadius: Boolean = true,
    needTopRadius: Boolean = true,
    onValueChanged: (Int) -> Unit
) {
    val bottomRadius = if (needBottomRadius) 10.dp else 4.dp
    val topRadius = if (needTopRadius) 10.dp else 4.dp

    val unit = intakeType.getUnit()?.let { stringResource(id = it) }
    val placeholder = "0${unit?:""}"

    val valueToShow = if (value == 0 ) "" else "$value${unit ?: ""}"

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedContainerColor = MaterialTheme.colorScheme.primary.copy(0.1f),
                focusedTextColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(
                topStart = topRadius,
                topEnd = topRadius,
                bottomStart = bottomRadius,
                bottomEnd = bottomRadius
            ),
            value = valueToShow,
            onValueChange = { newValue ->
                val newValueWithoutUnit = newValue.removeSuffix(unit?:"")

                if (newValueWithoutUnit.isEmpty()) {
                    onValueChanged(0)
                } else {
                    newValueWithoutUnit.toIntOrNull()?.let { validated ->
                        onValueChanged(validated)
                    }
                }
            },
            trailingIcon = {
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = stringResource(id = intakeType.getTitle()),
                    style = TextStyles.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
    }
}