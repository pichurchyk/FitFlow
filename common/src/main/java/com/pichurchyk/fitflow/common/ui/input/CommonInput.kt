package com.pichurchyk.fitflow.common.ui.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.common.ui.theme.AppTheme
import com.pichurchyk.fitflow.common.ui.theme.TextStyles

@Composable
fun CommonInput(
    modifier: Modifier = Modifier,
    value: String,
    title: String? = null,
    subtitle: String? = null,
    placeholder: String? = null,
    colors: CommonInputColors = DefaultCommonInputColors(),
    isEnable: Boolean = true,
    inputType: KeyboardType = KeyboardType.Number,
    onValueChanged: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        title?.let {
            Text(
                modifier = Modifier.padding(bottom = 6.dp),
                text = it,
                style = TextStyles.labelLarge,
                color = colors.titleColor
            )
        }

        OutlinedTextField(
            enabled = isEnable,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp)
                .height(44.dp),
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedContainerColor = colors.bgColor,
                unfocusedContainerColor = colors.bgColor,
                unfocusedTextColor = colors.valueColor,
                focusedTextColor = colors.valueColor,
                unfocusedPlaceholderColor = colors.placeholderColor,
                focusedPlaceholderColor = colors.placeholderColor,
                focusedIndicatorColor = colors.borderColor,
                unfocusedIndicatorColor = colors.borderColor
            ),
            textStyle = TextStyles.labelMedium,
            shape = RoundedCornerShape(4.dp),
            value = value,
            onValueChange = { newValue ->
                onValueChanged(newValue)
            },
            placeholder = {
                placeholder?.let {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = inputType,
                imeAction = ImeAction.Next
            ),
        )

        subtitle?.let {
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = it,
                style = TextStyles.labelMedium,
                color = colors.subtitleColor
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(20.dp)
        ) {
            CommonInput(
                title = "Title",
                value = "Value",
                subtitle = "Subtitle",
                placeholder = "Placeholder",
                colors = DefaultCommonInputColors()
            ) { }
        }
    }
}