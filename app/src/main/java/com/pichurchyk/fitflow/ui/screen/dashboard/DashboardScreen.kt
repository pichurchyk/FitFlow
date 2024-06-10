package com.pichurchyk.fitflow.ui.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.common.CustomSnackbar
import com.pichurchyk.fitflow.ui.common.ErrorBottomSheet
import com.pichurchyk.fitflow.ui.common.Loader
import com.pichurchyk.fitflow.viewmodel.dashboard.DashboardIntent
import com.pichurchyk.fitflow.viewmodel.dashboard.DashboardViewModel
import com.pichurchyk.fitflow.viewmodel.dashboard.DashboardViewState
import com.pichurchyk.nutrition.database.model.ext.getCarbs
import com.pichurchyk.nutrition.database.model.ext.getFat
import com.pichurchyk.nutrition.database.model.ext.getProtein
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
                        modifier = Modifier.fillMaxWidth(),
                        date = selectedDate
                    )

                    when (viewState) {
                        is DashboardViewState.Error -> {
                            val errorMessage = (viewState as DashboardViewState.Error).message
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
                            val showDataState = viewState as DashboardViewState.ShowData
                            MainContent(
                                modifier = Modifier,
                                state = showDataState
                            )
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
    private fun MainContent(
        modifier: Modifier = Modifier,
        state: DashboardViewState.ShowData
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is DashboardViewState.ShowData.Loading -> {
                    Loader()
                }

                is DashboardViewState.ShowData.Loaded -> {
                    CaloriesTotal(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp),
                        value = state.data.caloriesSum
                    )

                    IntakesRate(
                        modifier = Modifier.fillMaxWidth(),
                        fat = state.data.getFat(),
                        carbs = state.data.getCarbs(),
                        protein = state.data.getProtein(),
                    )
                }
            }
        }
    }

    @Composable
    private fun Header(
        modifier: Modifier = Modifier,
        date: Date
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                modifier = Modifier.offset(x = 10f.dp, y = 6f.dp),
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )

            Date(
                modifier = Modifier.padding(top = 10.dp, end = 12.dp),
                date = date
            )
        }
    }
}