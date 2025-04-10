package com.pichurchyk.fitflow.common.ui.button

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DefaultCommonButtonColors(
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    borderColor: Color = Color.Transparent,
    bgColor: Color = MaterialTheme.colorScheme.primary,
): CommonButtonColors {
    return CommonButtonColors(
        textColor, borderColor, bgColor
    )
}