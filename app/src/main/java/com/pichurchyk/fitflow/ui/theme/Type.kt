package com.pichurchyk.fitflow.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pichurchyk.fitflow.R

object AppFont {
    val interFont = FontFamily(
        Font(R.font.inter_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
        Font(R.font.inter_light, weight = FontWeight.Light, style = FontStyle.Normal),
        Font(R.font.inter_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
        Font(R.font.inter_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    )
}

private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = AppFont.interFont),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = AppFont.interFont),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = AppFont.interFont),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = AppFont.interFont),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = AppFont.interFont),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = AppFont.interFont),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = AppFont.interFont),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = AppFont.interFont),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = AppFont.interFont),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = AppFont.interFont),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = AppFont.interFont),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = AppFont.interFont),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = AppFont.interFont),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = AppFont.interFont),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = AppFont.interFont)
)