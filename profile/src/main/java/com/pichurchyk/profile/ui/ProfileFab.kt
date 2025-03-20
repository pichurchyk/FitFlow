package com.pichurchyk.profile.ui

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
import androidx.compose.material.icons.rounded.Person
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
import com.pichurchyk.profile.R

@Composable
fun ProfileFab(
    modifier: Modifier = Modifier,
) {
    val toFullScreenTransitionTime = 400
    val toFabTransitionTime = toFullScreenTransitionTime / 2

    var containerState by remember {
        mutableStateOf<ProfileFabContainerState>(
            ProfileFabContainerState.Fab
        )
    }
    val transition = updateTransition(containerState, label = "container transform")

    val animatedColor by transition.animateColor(
        label = "color",
    ) { state ->
        when (state) {
            is ProfileFabContainerState.Fab -> MaterialTheme.colorScheme.primary
            is ProfileFabContainerState.Screen -> MaterialTheme.colorScheme.background
        }
    }

    val cornerRadius by transition.animateDp(
        label = "corner radius",
        transitionSpec = {
            when (targetState) {
                is ProfileFabContainerState.Fab -> tween(
                    durationMillis = toFullScreenTransitionTime,
                    easing = EaseOutCubic,
                )

                is ProfileFabContainerState.Screen -> tween(
                    durationMillis = toFabTransitionTime,
                    easing = EaseInCubic,
                )
            }
        }
    ) { state ->
        when (state) {
            is ProfileFabContainerState.Fab -> 16.dp
            is ProfileFabContainerState.Screen -> 0.dp
        }
    }

    val elevation by transition.animateDp(
        label = "elevation",
        transitionSpec = {
            when (targetState) {
                is ProfileFabContainerState.Fab -> tween(
                    durationMillis = toFullScreenTransitionTime,
                    easing = EaseOutCubic,
                )

                is ProfileFabContainerState.Screen -> tween(
                    durationMillis = toFabTransitionTime,
                    easing = EaseOutCubic,
                )
            }
        }
    ) { state ->
        when (state) {
            is ProfileFabContainerState.Fab -> 6.dp
            is ProfileFabContainerState.Screen -> 0.dp
        }
    }

    val padding by transition.animateDp(
        label = "padding",
    ) { state ->
        when (state) {
            is ProfileFabContainerState.Fab -> 16.dp
            is ProfileFabContainerState.Screen -> 0.dp
        }
    }

    transition.AnimatedContent(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(start = padding, bottom = padding)
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
            is ProfileFabContainerState.Fab -> {
                    FloatingActionButton(
                        onClick = {
                            containerState = ProfileFabContainerState.Screen
                        },
                        shape = RoundedCornerShape(10.dp),
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = stringResource(id = R.string.profile),
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }

            is ProfileFabContainerState.Screen -> {
                ProfileScreen(
                    onBackPressed = {
                        containerState = ProfileFabContainerState.Fab
                    }
                )
            }
        }
    }
}