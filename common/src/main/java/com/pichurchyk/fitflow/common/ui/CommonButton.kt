package com.pichurchyk.fitflow.common.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.common.ui.button.CommonButtonColors
import com.pichurchyk.fitflow.common.ui.button.DefaultCommonButtonColors
import com.pichurchyk.fitflow.common.ui.theme.AppTheme
import com.pichurchyk.fitflow.common.ui.theme.TextStyles

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    text: String,
    colors: CommonButtonColors = DefaultCommonButtonColors(),
    doOnClick: () -> Unit
) {
    val bottomPadding = if (colors.borderColor != Color.Transparent) 1.dp else 0.dp
    Button(
        modifier = modifier
            .height(38.dp)
            .border(1.dp, colors.borderColor, RoundedCornerShape(10.dp))
            .padding(bottom = bottomPadding)
        ,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.bgColor
        ),
        onClick = doOnClick
    ) {
        Text(
            text = text,
            style = TextStyles.labelSmall,
            color = colors.textColor
        )
    }
}

@Composable
@Preview
private fun RoundedButtonPreview() {
    AppTheme {
        CommonButton(
            text = "Text",
            colors = DefaultCommonButtonColors(
                textColor = MaterialTheme.colorScheme.primary,
                bgColor = MaterialTheme.colorScheme.onPrimary,
                borderColor = MaterialTheme.colorScheme.primary
            )
        ) {}
    }
}