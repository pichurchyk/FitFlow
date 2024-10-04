package com.pichurchyk.fitflow.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pichurchyk.fitflow.ui.common.CustomSnackbar
import com.pichurchyk.fitflow.ui.common.Loader
import com.pichurchyk.fitflow.ui.screen.auth.AnimatedLogo
import com.pichurchyk.fitflow.viewmodel.splash.SplashIntent
import com.pichurchyk.fitflow.viewmodel.splash.SplashViewModel
import com.pichurchyk.fitflow.viewmodel.splash.SplashViewState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel(),
    openDashboard: () -> Unit,
    openAuthScreen: () -> Unit,
) {
    val viewState by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(SplashIntent.CheckIfUserSignedIn)
    }

    var expandedLogo by remember {
        mutableStateOf(true)
    }

    var isReadyToNavigate by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(isReadyToNavigate) {
        if (isReadyToNavigate) {
            when (viewModel.state.value) {
                is SplashViewState.UserSignedIn -> {
                    openDashboard()
                }

                is SplashViewState.UserNotSignedIn -> {
                    openAuthScreen()
                }

                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) { data -> CustomSnackbar(data) } },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedLogo(
                    modifier = Modifier.weight(1f),
                    isExpanded = expandedLogo,
                    onLogoTransitionFinished = {
                        isReadyToNavigate = true
                    }
                )

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    when (viewState) {
                        is SplashViewState.Loading -> {
                            Loader()
                        }

                        else -> {
                            expandedLogo = false
                        }
                    }
                }
            }
        }
    )
}
