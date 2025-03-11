package com.pichurchyk.fitflow.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.theme.AppFont.interFont

object AppFont {
    val interFont = FontFamily(
        Font(R.font.inter_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
        Font(R.font.inter_light, weight = FontWeight.Light, style = FontStyle.Normal),
        Font(R.font.inter_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
        Font(R.font.inter_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    )
}

object TextStyles {
    val bodyLarge = TextStyle(
        fontFamily = interFont,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    )

    val bodyMedium = TextStyle(
        fontFamily = interFont,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp,
    )

    val bodySmall = TextStyle(
        fontFamily = interFont,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.4.sp,
    )

    val headlineSmall = TextStyle(
        fontFamily = interFont,
        fontWeight = FontWeight.W400,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
    )

    val titleLarge = TextStyle(
        fontFamily = interFont,
        fontWeight = FontWeight.W400,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    )

    val titleMedium = TextStyle(
        fontFamily = interFont,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    )

    val labelMedium = TextStyle(
        fontFamily = interFont,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    )
}