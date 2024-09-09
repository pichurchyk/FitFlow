package com.pichurchyk.fitflow.ui.screen.dashboard.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.ui.theme.AppTheme
import com.pichurchyk.fitflow.ui.theme.TextStyles

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
            CircularProgress(
                modifier = modifier,
                progress = { progressAnimation },
                color = color,
                limit = limit,
                isDone = limit <= value
            )
        }
    }
}

enum class AnimatedProgressIndicatorType {
    LINEAR,
    RADIAL
}

@Composable
private fun CircularProgress(
    modifier: Modifier = Modifier,
    progress: () -> Float,
    color: Color = MaterialTheme.colorScheme.primary,
    limit: Int,
    isDone: Boolean
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            progress = progress,
            color = color,
            trackColor = color.copy(alpha = 0.3f)
        )

        if (isDone) {
            Box(
                modifier = Modifier
                    .background(color.copy(0.3f), RoundedCornerShape(100))
                    .size(36.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.Check, contentDescription = "", tint = color)
            }
        } else {
            Text(
                text = limit.toString(),
                style = TextStyles.labelMedium,
                color = color
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        AnimatedProgressIndicator(
            type = AnimatedProgressIndicatorType.RADIAL,
            modifier = Modifier,
            value = 160,
            color = Color.Magenta,
            limit = 100
        )
    }
}