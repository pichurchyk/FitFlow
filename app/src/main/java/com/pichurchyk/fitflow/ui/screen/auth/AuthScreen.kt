package com.pichurchyk.fitflow.ui.screen.auth

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.common.CustomSnackbar
import com.pichurchyk.fitflow.ui.common.Error
import com.pichurchyk.fitflow.ui.common.Loader
import com.pichurchyk.fitflow.ui.theme.AppTheme
import com.pichurchyk.fitflow.viewmodel.auth.AuthIntent
import com.pichurchyk.fitflow.viewmodel.auth.AuthViewModel
import com.pichurchyk.fitflow.viewmodel.auth.AuthViewState
import kotlinx.coroutines.launch

object AuthScreen : Screen {
    private fun readResolve(): Any = AuthScreen

    @Composable
    override fun Content() {
        val viewModel: AuthViewModel = getScreenModel()
        val viewState by viewModel.state.collectAsState()

        val snackbarHostState = remember { SnackbarHostState() }

        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        val googleAuthClient by remember {
            mutableStateOf(GoogleAuthClient(context))
        }

        val signedInUser = googleAuthClient.signedInAccount.value

        var expandedLogo by remember {
            mutableStateOf(true)
        }

        LaunchedEffect(signedInUser) {
            signedInUser?.let { account ->
                viewModel.handleIntent(AuthIntent.Auth(account))
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
                    Logo(
                        modifier = Modifier.weight(1f),
                        isExpanded = expandedLogo
                    )

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        when (viewState) {
                            is AuthViewState.Loading -> {
                                Loader()
                            }

                            is AuthViewState.Success -> {
                                expandedLogo = false
                            }

                            is AuthViewState.Init -> {
                                GoogleSignInButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            googleAuthClient.startSignIn()
                                        }
                                    }
                                )
                            }

                            is AuthViewState.Error -> {
                                val errorMessage = (viewState as AuthViewState.Error).message
                                Error(
                                    errorMessage = errorMessage,
                                    onDismiss = { viewModel.handleIntent(AuthIntent.Clear) }
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun Logo(modifier: Modifier = Modifier, isExpanded: Boolean) {
    val fontSize by animateFloatAsState(
        targetValue = if (isExpanded) 70f else 30f,
        animationSpec = tween(durationMillis = 1000)
    )

    val offsetX by animateFloatAsState(
        targetValue = if (isExpanded) 78f else 10f,
        animationSpec = tween(durationMillis = 1000)
    )

    val offsetY by animateFloatAsState(
        targetValue = if (isExpanded) 320f else 6f,
        animationSpec = tween(durationMillis = 1000)
    )

    val density = LocalDensity.current

    Box(
        modifier = modifier
            .animateContentSize()
            .wrapContentWidth()
            .wrapContentHeight()
            .offset {
                IntOffset(
                    x = with(density) { offsetX.dp.toPx().toInt() },
                    y = with(density) { offsetY.dp.toPx().toInt() }
                )
            },
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        elevation = ButtonDefaults.buttonElevation(
            2.dp
        ),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = stringResource(
                id = R.string.google_sign_in
            ),
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(text = stringResource(id = R.string.google_sign_in), fontWeight = FontWeight.Normal)
    }
}

@Composable
@Preview(showSystemUi = true)
private fun AuthScreenPreview() {
    AppTheme {
        AuthScreen.Content()
    }
}