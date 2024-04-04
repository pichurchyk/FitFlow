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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.common.CustomSnackbar
import com.pichurchyk.fitflow.ui.theme.AppTheme

object AuthScreen : Screen {
    @Composable
    override fun Content() {
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

//        coroutineScope.launch {
//            snackbarHostState.showSnackbar(
//                Snackbar(
//                    message = "",
//                    isError = true
//                )
//            )
//        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) { data -> CustomSnackbar(data) } },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 50.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        GoogleSignInButton(
                            onClick = {}
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun GoogleSignInButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier,
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