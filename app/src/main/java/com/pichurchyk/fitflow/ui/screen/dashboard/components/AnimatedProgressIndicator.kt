package com.pichurchyk.fitflow.ui.screen.dashboard.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.ui.theme.AppTheme

@Composable
fun AnimatedProgressIndicator(
    type: AnimatedProgressIndicatorType,
    modifier: Modifier,
    value: Int,
    color: Color,
    limit: Int
) {
    var progress by remember { mutableFloatStateOf(0F) }
    val progressAnimDuration = 500
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing),
        label = "",
    )
    LaunchedEffect(value) {
        progress = value.toFloat() / limit.toFloat()
    }

    when (type) {
        AnimatedProgressIndicatorType.LINEAR -> {
            LinearProgressIndicator(
                modifier = modifier
                    .height(4.dp),
                progress = { progressAnimation },
                color = color,
                strokeCap = StrokeCap.Round,
                trackColor = color.copy(alpha = 0.3f)
            )
        }

        AnimatedProgressIndicatorType.RADIAL -> {
            CircularProgressIndicator(
                modifier = modifier,
                progress = { progressAnimation },
                color = color,
                strokeCap = StrokeCap.Round,
                strokeWidth = 4.dp,
                trackColor = color.copy(alpha = 0.3f)
            )
        }
    }
}

enum class AnimatedProgressIndicatorType {
    LINEAR,
    RADIAL
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        AnimatedProgressIndicator(
            type = AnimatedProgressIndicatorType.LINEAR,
            modifier = Modifier,
            value = 60,
            color = Color.Magenta,
            limit = 100
        )
    }
}