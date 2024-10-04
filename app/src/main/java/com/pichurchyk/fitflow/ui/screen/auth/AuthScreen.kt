package com.pichurchyk.fitflow.ui.screen.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.common.CustomSnackbar
import com.pichurchyk.fitflow.ui.common.ErrorBottomSheet
import com.pichurchyk.fitflow.ui.common.Header
import com.pichurchyk.fitflow.ui.common.Loader
import com.pichurchyk.fitflow.ui.theme.AppTheme
import com.pichurchyk.fitflow.viewmodel.auth.AuthIntent
import com.pichurchyk.fitflow.viewmodel.auth.AuthViewModel
import com.pichurchyk.fitflow.viewmodel.auth.AuthViewState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = koinViewModel(),
    openDashboard: () -> Unit,
) {
    val viewState by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val googleAuthClient by remember {
        mutableStateOf(GoogleAuthClient(context))
    }

    LaunchedEffect(googleAuthClient.signedInAccount.value) {
        googleAuthClient.signedInAccount.value?.let { account ->
            viewModel.handleIntent(AuthIntent.Auth(account))
        }
    }

    LaunchedEffect(viewState) {
        if (viewState is AuthViewState.Success) {
            openDashboard()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) { data -> CustomSnackbar(data) } },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Header(
                    modifier = Modifier,
                    title = stringResource(R.string.app_name),
                )

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    when (viewState) {
                        is AuthViewState.Loading -> {
                            Loader()
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
                            ErrorBottomSheet(
                                errorMessage = errorMessage,
                                onDismiss = { viewModel.handleIntent(AuthIntent.Clear) }
                            )
                        }

                        else -> {}
                    }
                }
            }
        }
    )
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
        AuthScreen(
            openDashboard = {}
        )
    }
}