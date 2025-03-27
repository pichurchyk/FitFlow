package com.pichurchyk.fitflow.common.ui.input

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DefaultCommonInputColors(
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    placeholderColor: Color = MaterialTheme.colorScheme.onBackground.copy(0.4f),
    subtitleColor: Color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
    valueColor: Color = MaterialTheme.colorScheme.onBackground,
    borderColor: Color = MaterialTheme.colorScheme.onBackground,
    bgColor: Color = MaterialTheme.colorScheme.background,
): CommonInputColors {
    return CommonInputColors(
        titleColor, placeholderColor, subtitleColor, valueColor, borderColor, bgColor
    )
}