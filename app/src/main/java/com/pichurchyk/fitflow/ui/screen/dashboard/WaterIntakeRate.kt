package com.pichurchyk.fitflow.ui.screen.dashboard


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pichurchyk.fitflow.ui.ext.getColor
import com.pichurchyk.fitflow.ui.ext.getTitle
import com.pichurchyk.fitflow.ui.theme.AppTheme
import com.pichurchyk.nutrition.database.model.IntakeType


@Composable
fun WaterIntakeRate(modifier: Modifier, value: Int, limit: Int) {
    Box(modifier = modifier.width(34.dp), contentAlignment = Alignment.Center) {
        AnimatedProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            value = value,
            limit = limit
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(align = Alignment.CenterStart, unbounded = true),
                text = value.toString(),
                color = MaterialTheme.colorScheme.background,
                fontSize = 48.sp,
                letterSpacing = (-4).sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth()
                    .background(MaterialTheme.colorScheme.background, RoundedCornerShape(100.dp))
                    .padding(horizontal = 10.dp),
            ) {
                Text(
                    text = stringResource(id = IntakeType.WATER.getTitle()),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = IntakeType.WATER.getColor()
                )
            }
            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(align = Alignment.CenterEnd, unbounded = true),
                text = limit.toString(),
                color = MaterialTheme.colorScheme.background,
                fontSize = 48.sp,
                letterSpacing = (-4).sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
private fun AnimatedProgressIndicator(
    modifier: Modifier,
    value: Int,
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

    LinearProgressIndicator(
        modifier = modifier,
        progress = { progressAnimation },
        color = IntakeType.WATER.getColor(),
        trackColor = IntakeType.WATER.getColor().copy(alpha = 0.3f)
    )
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        WaterIntakeRate(modifier = Modifier, value = 2132, limit = 3000)
    }
}