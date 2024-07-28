package com.pichurchyk.fitflow.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.screen.dashboard.components.AnimatedProgressIndicator
import com.pichurchyk.fitflow.ui.screen.dashboard.components.AnimatedProgressIndicatorType
import com.pichurchyk.fitflow.ui.theme.AppTheme

@Composable
fun RadialProgress(modifier: Modifier = Modifier, value: Int, limit: Int, color: Color = MaterialTheme.colorScheme.primary) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        AnimatedProgressIndicator(
            modifier = Modifier
                .size(50.dp),
            type = AnimatedProgressIndicatorType.RADIAL,
            value = value,
            color = color,
            limit = limit,
        )

        if (value < limit) {
            Text(
                modifier = Modifier,
                text = limit.toString(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
            )
        } else {
            Icon(
                modifier = Modifier.size(30.dp),
                tint = color,
                imageVector = Icons.Rounded.Check,
                contentDescription = stringResource(id = R.string.done)
            )
        }
    }
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        RadialProgress(
            modifier = Modifier,
            value = 60,
            limit = 100,
            color = MaterialTheme.colorScheme.error
        )
    }
}