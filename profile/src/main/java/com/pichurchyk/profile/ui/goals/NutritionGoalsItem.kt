package com.pichurchyk.profile.ui.goals

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.common.ui.input.CommonInput
import com.pichurchyk.fitflow.common.ui.input.DefaultCommonInputColors
import com.pichurchyk.fitflow.common.ui.theme.AppTheme
import com.pichurchyk.fitflow.common.ui.theme.color_grey

@Composable
fun NutritionGoalsItem(
    modifier: Modifier,
    title: String,
    value: String,
    subtitle: String,
    bgColor: Color,
    onValueChanged: (String) -> Unit,
) {
    Box(
        modifier = modifier
            .background(color = bgColor.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
            .border(1.dp, bgColor, RoundedCornerShape(6.dp))
            .padding(vertical = 10.dp, horizontal = 16.dp)
    ) {
        CommonInput(
            modifier = modifier,
            value = if (value == "0") "" else value,
            title = title,
            placeholder = if (value == "0") value else null,
            subtitle = subtitle,
            onValueChanged = onValueChanged,
            colors = DefaultCommonInputColors(
                bgColor = MaterialTheme.colorScheme.background,
                subtitleColor = MaterialTheme.colorScheme.onBackground,
                titleColor = bgColor,
                borderColor = color_grey,
                valueColor = MaterialTheme.colorScheme.onBackground,
                placeholderColor = MaterialTheme.colorScheme.onBackground.copy(0.5f),
            )
        )
    }
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        NutritionGoalsItem(
            modifier = Modifier,
            title = "Title",
            value = "Value",
            subtitle = "Subtitle",
            bgColor = MaterialTheme.colorScheme.primary,
            onValueChanged = {}
        )
    }
}