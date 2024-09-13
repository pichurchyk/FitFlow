package com.pichurchyk.fitflow.ui.screen.auth

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.theme.TextStyles

@Composable
fun AnimatedLogo(modifier: Modifier = Modifier, isExpanded: Boolean, onLogoTransitionFinished: () -> Unit) {
    var isReadyToAnimateText by remember {
        mutableStateOf(false)
    }

    val fontSize by animateFloatAsState(
        targetValue = if (!isReadyToAnimateText) 70f else 20f,
        animationSpec = tween(durationMillis = 100),
        finishedListener = {
            onLogoTransitionFinished()
        }
    )

    val offset by animateDpAsState(
        targetValue = if (isExpanded) 0.dp else (-156).dp,
        animationSpec = tween(durationMillis = 300),
        finishedListener = {
            isReadyToAnimateText = true
        }
    )

    Box(
        modifier = modifier
            .animateContentSize()
            .fillMaxSize(),
        contentAlignment = if (isExpanded) Alignment.BottomCenter else Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .offset(y = offset),
            text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colorScheme.onBackground,
            style = TextStyles.headlineSmall,
            fontSize = fontSize.sp,
        )
    }
}