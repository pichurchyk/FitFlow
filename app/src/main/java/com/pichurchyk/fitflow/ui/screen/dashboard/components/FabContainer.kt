package com.pichurchyk.fitflow.ui.screen.dashboard.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.ext.doOnClick
import com.pichurchyk.fitflow.ui.screen.addintake.AddIntakeScreen
import com.pichurchyk.fitflow.ui.screen.addwaterintake.AddWaterIntakeScreen
import java.util.Date

@Composable
fun AddIntakeFab(
    selectedDate: Date,
    modifier: Modifier = Modifier,
) {
    val toFullScreenTransitionTime = 400
    val toFabTransitionTime = toFullScreenTransitionTime / 2

    var containerState by remember {
        mutableStateOf<DashboardFabContainerState>(
            DashboardFabContainerState.Fab(isExpanded = false)
        )
    }
    val transition = updateTransition(containerState, label = "container transform")

    val animatedColor by transition.animateColor(
        label = "color",
    ) { state ->
        when (state) {
            is DashboardFabContainerState.Fab -> MaterialTheme.colorScheme.primary
            is DashboardFabContainerState.AddIntakeScreen -> MaterialTheme.colorScheme.background
            is DashboardFabContainerState.AddWaterIntakeScreen -> MaterialTheme.colorScheme.background
        }
    }

    val cornerRadius by transition.animateDp(
        label = "corner radius",
        transitionSpec = {
            when (targetState) {
                is DashboardFabContainerState.Fab -> tween(
                    durationMillis = toFullScreenTransitionTime,
                    easing = EaseOutCubic,
                )

                is DashboardFabContainerState.AddIntakeScreen -> tween(
                    durationMillis = toFabTransitionTime,
                    easing = EaseInCubic,
                )

                is DashboardFabContainerState.AddWaterIntakeScreen -> tween(
                    durationMillis = toFabTransitionTime,
                    easing = EaseInCubic,
                )
            }
        }
    ) { state ->
        when (state) {
            is DashboardFabContainerState.Fab -> 16.dp
            is DashboardFabContainerState.AddIntakeScreen -> 0.dp
            is DashboardFabContainerState.AddWaterIntakeScreen -> 0.dp
        }
    }

    val elevation by transition.animateDp(
        label = "elevation",
        transitionSpec = {
            when (targetState) {
                is DashboardFabContainerState.Fab -> tween(
                    durationMillis = toFullScreenTransitionTime,
                    easing = EaseOutCubic,
                )

                is DashboardFabContainerState.AddIntakeScreen -> tween(
                    durationMillis = toFabTransitionTime,
                    easing = EaseOutCubic,
                )

                is DashboardFabContainerState.AddWaterIntakeScreen -> tween(
                    durationMillis = toFabTransitionTime,
                    easing = EaseOutCubic,
                )
            }
        }
    ) { state ->
        when (state) {
            is DashboardFabContainerState.Fab -> 6.dp
            is DashboardFabContainerState.AddIntakeScreen -> 0.dp
            is DashboardFabContainerState.AddWaterIntakeScreen -> 0.dp
        }
    }

    val padding by transition.animateDp(
        label = "padding",
    ) { state ->
        when (state) {
            is DashboardFabContainerState.Fab -> 16.dp
            is DashboardFabContainerState.AddIntakeScreen -> 0.dp
            is DashboardFabContainerState.AddWaterIntakeScreen -> 0.dp
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
            is DashboardFabContainerState.Fab -> {
                val fabRotation by animateFloatAsState(
                    if (state.isExpanded) {
                        45f
                    } else {
                        0f
                    },
                    label = "Fab rotation"
                )

                val enterTransition = remember {
                    expandVertically(
                        expandFrom = Alignment.Bottom,
                        animationSpec = tween(150, easing = FastOutSlowInEasing)
                    )
                }

                val exitTransition = remember {
                    shrinkVertically(
                        shrinkTowards = Alignment.Bottom,
                        animationSpec = tween(150, easing = FastOutSlowInEasing)
                    )
                }

                Column(
                    modifier = Modifier.background(Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom)
                ) {
                    AnimatedVisibility(
                        visible = state.isExpanded,
                        enter = enterTransition,
                        exit = exitTransition
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .doOnClick {
                                        containerState = DashboardFabContainerState.AddWaterIntakeScreen
                                    },
                                painter = painterResource(R.drawable.ic_water),
                                contentDescription = stringResource(id = R.string.add_intake),
                                tint = MaterialTheme.colorScheme.onPrimary,
                            )

                            Icon(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .doOnClick {
                                        containerState = DashboardFabContainerState.AddIntakeScreen
                                    },
                                painter = painterResource(R.drawable.ic_food),
                                contentDescription = stringResource(id = R.string.add_intake),
                                tint = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    }

                    FloatingActionButton(
                        onClick = {
                            containerState = DashboardFabContainerState.Fab(true)
                        },
                        shape = RoundedCornerShape(10.dp),
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            modifier = Modifier.rotate(fabRotation),
                            imageVector = Icons.Rounded.Add,
                            contentDescription = stringResource(id = R.string.add_intake),
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
            }

            is DashboardFabContainerState.AddIntakeScreen -> {
                AddIntakeScreen(
                    selectedDate = selectedDate,
                    closeScreen = {
                        containerState = DashboardFabContainerState.Fab(false)
                    }
                )
            }

            is DashboardFabContainerState.AddWaterIntakeScreen -> {
                AddWaterIntakeScreen(
                    selectedDate = selectedDate,
                    closeScreen = {
                        containerState = DashboardFabContainerState.Fab(false)
                    }
                )
            }
        }
    }
}