package com.pichurchyk.fitflow.common.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.common.ui.theme.AppTheme

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    size: Dp = 30.dp
) {
    CircularProgressIndicator(
        modifier = modifier.size(size),
        strokeWidth = size / 7,
        strokeCap = StrokeCap.Round,
        color = MaterialTheme.colorScheme.primary,
        trackColor = Color.Transparent
    )
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        Loader(
            modifier = Modifier,
            size = 132.dp
        )
    }
}