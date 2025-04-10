package com.pichurchyk.fitflow.common.ui.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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

        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                onValueChanged(newValue)
            },
            enabled = isEnable,
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(colors.bgColor, shape = RoundedCornerShape(4.dp))
                .border(1.dp, colors.borderColor, shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp, vertical = 10.dp),
            textStyle = TextStyles.labelSmall.copy(color = colors.valueColor),
            keyboardOptions = KeyboardOptions(
                keyboardType = inputType,
                imeAction = ImeAction.Next
            ),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder.orEmpty(),
                        style = TextStyles.labelSmall,
                        color = colors.placeholderColor
                    )
                }
                innerTextField()
            }
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