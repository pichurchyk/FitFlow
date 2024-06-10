package com.pichurchyk.fitflow.ui.screen.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.common.ext.toDateString
import com.pichurchyk.fitflow.ui.common.CustomSnackbar
import com.pichurchyk.fitflow.ui.common.ErrorBottomSheet
import com.pichurchyk.fitflow.viewmodel.auth.AuthIntent
import com.pichurchyk.fitflow.viewmodel.auth.AuthViewState
import com.pichurchyk.fitflow.viewmodel.dashboard.DashboardIntent
import com.pichurchyk.fitflow.viewmodel.dashboard.DashboardViewModel
import com.pichurchyk.fitflow.viewmodel.dashboard.DashboardViewState
import java.util.Date

object DashboardScreen : Screen {
    private fun readResolve(): Any = DashboardScreen

    @Composable
    override fun Content() {
        val viewModel: DashboardViewModel = getScreenModel()
        val viewState by viewModel.state.collectAsState()
        val selectedDate by viewModel.selectedDate.collectAsState()

        val snackbarHostState = remember { SnackbarHostState() }

        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            viewModel.handleIntent(DashboardIntent.LoadData)
        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) { data -> CustomSnackbar(data) } },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Header(
                        modifier = Modifier,
                        date = selectedDate
                    )

                    when(viewState) {
                        is DashboardViewState.Error -> {
                            val errorMessage = (viewState as AuthViewState.Error).message
                            ErrorBottomSheet(
                                errorMessage = errorMessage,
                                dismissButtonText = stringResource(id = R.string.try_again),
                                onDismiss = {
                                    viewModel.handleIntent(DashboardIntent.LoadData)
                                }
                            )
                        }

                        is DashboardViewState.Init -> {}

                        is DashboardViewState.ShowData -> {

                        }
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
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
        )
    }

    @Composable
    private fun Header(
        modifier: Modifier = Modifier,
        date: Date
    ) {
        Row {
            Text(
                modifier = Modifier.offset(x = 10f.dp, y = 6f.dp),
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )

            Date(
                date = date
            )
        }
    }

    @Composable
    private fun Date(
        modifier: Modifier = Modifier,
        date: Date,
    ) {
        Text(
            modifier = modifier,
            text = date.toDateString(),
            fontSize = 25.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}