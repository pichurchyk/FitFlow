package com.pichurchyk.fitflow.ui.screen.dashboard

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pichurchyk.fitflow.ui.ext.getColor
import com.pichurchyk.fitflow.ui.ext.getTitle
import com.pichurchyk.nutrition.database.model.IntakeType

@Composable
fun IntakesRate(
    modifier: Modifier = Modifier,
    fat: Int,
    protein: Int,
    carbs: Int
) {
    val carbsRate = IntakeRate(type = IntakeType.CARBS, value = carbs)
    val fatRate = IntakeRate(type = IntakeType.FAT, value = fat)
    val proteinRate = IntakeRate(type = IntakeType.PROTEIN, value = protein)
    Column(modifier = modifier) {
        IntakeRateItem(modifier = Modifier.fillMaxWidth(), intake = carbsRate, limit = 120.0)
        IntakeRateItem(modifier = Modifier.fillMaxWidth(), intake = proteinRate, limit = 186.0)
        IntakeRateItem(modifier = Modifier.fillMaxWidth(), intake = fatRate, limit = 65.0)
    }
}

@Composable
private fun IntakeRateItem(modifier: Modifier, intake: IntakeRate, limit: Double) {
    Box(modifier = modifier.height(34.dp), contentAlignment = Alignment.Center) {
        AnimatedProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            intake = intake,
            limit = limit
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = intake.value.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth()
                    .background(MaterialTheme.colorScheme.background, RoundedCornerShape(100.dp))
                    .padding(horizontal = 10.dp),
            ) {
                Text(
                    text = stringResource(id = intake.type.getTitle()),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = intake.type.getColor()
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text = limit.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
private fun AnimatedProgressIndicator(
    modifier: Modifier,
    intake: IntakeRate,
    limit: Double
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var progress by remember { mutableStateOf(0F) }
    val progressAnimDuration = 500
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing),
    )
    LaunchedEffect(lifecycleOwner) {
        progress = intake.value.toFloat() / limit.toFloat()
    }

    LinearProgressIndicator(
        modifier = modifier,
        progress = { progressAnimation },
        color = intake.type.getColor(),
        trackColor = intake.type.getColor().copy(alpha = 0.3f)
    )
}

private data class IntakeRate(
    val value: Int,
    val type: IntakeType
)
