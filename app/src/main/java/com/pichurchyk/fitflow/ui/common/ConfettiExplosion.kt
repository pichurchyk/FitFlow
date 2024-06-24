package com.pichurchyk.fitflow.ui.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import kotlin.random.Random

@Composable
fun ConfettiExplosion(
    modifier: Modifier = Modifier,
    numConfetti: Int = 500,
    explosionDuration: Int, // in milliseconds
) {
    val confettiList = remember {
        List(numConfetti) {
            Confetti(
                x = 0f,
                y = 0f,
                color = Color(
                    red = Random.nextFloat(),
                    green = Random.nextFloat(),
                    blue = Random.nextFloat()
                ),
                size = Random.nextFloat() * 10 + 5,
                velocityX = Random.nextFloat() * 4 - 2,
                velocityY = Random.nextFloat() * 4 - 2,
                alpha = 1f
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "")

    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(explosionDuration, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .rotate(180f)
            .zIndex(1f),
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            confettiList.forEach { confetti ->
                val progress = animationProgress * explosionDuration / 1000
                val x = confetti.x + confetti.velocityX * progress * size.width / 2
                val y = confetti.y + confetti.velocityY * progress * size.height / 2
                val alpha = 1f - progress // Reduce alpha over time

                drawCircle(
                    color = confetti.color.copy(alpha = alpha),
                    radius = confetti.size,
                    center = Offset(x, y)
                )
            }
        }
    }
}

private data class Confetti(
    val x: Float,
    val y: Float,
    val color: Color,
    val size: Float,
    val velocityX: Float,
    val velocityY: Float,
    val alpha: Float,
)
