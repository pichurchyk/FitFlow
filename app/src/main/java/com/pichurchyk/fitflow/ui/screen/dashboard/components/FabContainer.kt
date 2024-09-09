package com.pichurchyk.fitflow.ui.screen.dashboard.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.common.ContainerState
import com.pichurchyk.fitflow.ui.screen.addintake.AddIntakeScreen
import java.util.Date

@Composable
fun AddIntakeFab(
    selectedDate: Date,
    modifier: Modifier = Modifier,
) {
    val toFullScreenTransitionTime = 400
    val toFabTransitionTime = toFullScreenTransitionTime / 2

    var containerState by remember { mutableStateOf(ContainerState.FAB) }
    val transition = updateTransition(containerState, label = "container transform")

    val animatedColor by transition.animateColor(
        label = "color",
    ) { state ->
        when (state) {
            ContainerState.FAB -> MaterialTheme.colorScheme.primary
            ContainerState.FULL_SCREEN -> MaterialTheme.colorScheme.background
        }
    }

    val cornerRadius by transition.animateDp(
        label = "corner radius",
        transitionSpec = {
            when (targetState) {
                ContainerState.FAB -> tween(
                    durationMillis = toFullScreenTransitionTime,
                    easing = EaseOutCubic,
                )

                ContainerState.FULL_SCREEN -> tween(
                    durationMillis = toFabTransitionTime,
                    easing = EaseInCubic,
                )
            }
        }
    ) { state ->
        when (state) {
            ContainerState.FAB -> 16.dp
            ContainerState.FULL_SCREEN -> 0.dp
        }
    }

    val elevation by transition.animateDp(
        label = "elevation",
        transitionSpec = {
            when (targetState) {
                ContainerState.FAB -> tween(
                    durationMillis = toFullScreenTransitionTime,
                    easing = EaseOutCubic,
                )

                ContainerState.FULL_SCREEN -> tween(
                    durationMillis = toFabTransitionTime,
                    easing = EaseOutCubic,
                )
            }
        }
    ) { state ->
        when (state) {
            ContainerState.FAB -> 6.dp
            ContainerState.FULL_SCREEN -> 0.dp
        }
    }

    val padding by transition.animateDp(
        label = "padding",
    ) { state ->
        when (state) {
            ContainerState.FAB -> 16.dp
            ContainerState.FULL_SCREEN -> 0.dp
        }
    }

    transition.AnimatedContent(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(end = padding, bottom = padding)
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(cornerRadius)
            )
            .drawBehind { drawRect(animatedColor) },
        transitionSpec = {
            (
                    fadeIn(animationSpec = tween(320, delayMillis = 90)) +
                            scaleIn(
                                initialScale = 0.92f,
                                animationSpec = tween(220, delayMillis = 90)
                            )
                    )
                .togetherWith(fadeOut(animationSpec = tween(300)))
                .using(SizeTransform(clip = false, sizeAnimationSpec = { _, _ ->
                    tween(
                        durationMillis = 200,
                        easing = FastOutSlowInEasing
                    )
                }))
        }
    ) { state ->
        when (state) {
            ContainerState.FAB -> {
                FloatingActionButton(
                    onClick = {
                        containerState = ContainerState.FULL_SCREEN
                    },
                    shape = RoundedCornerShape(10.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = stringResource(id = R.string.add_intake),
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }

            ContainerState.FULL_SCREEN -> {
                AddIntakeScreen(
                    selectedDate = selectedDate,
                    closeScreen = {
                        containerState = ContainerState.FAB
                    }
                )
            }
        }
    }
}