package com.pichurchyk.fitflow.ui.common

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.ui.theme.AppTheme

@Composable
fun Loader(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier,
        strokeWidth = 8.dp,
        strokeCap = StrokeCap.Round,
        color = MaterialTheme.colorScheme.primary,
        trackColor = Color.Transparent
    )
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        Loader()
    }
}