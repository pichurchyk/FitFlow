package com.pichurchyk.fitflow.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pichurchyk.fitflow.ui.theme.AppTheme

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    text: String,
    doOnClick: () -> Unit
) {
    Button(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
        onClick = doOnClick
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
@Preview
private fun RoundedButtonPreview() {
    AppTheme {
        RoundedButton(text = "Text") {}
    }
}