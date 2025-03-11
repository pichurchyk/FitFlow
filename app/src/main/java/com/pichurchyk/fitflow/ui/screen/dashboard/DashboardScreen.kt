package com.pichurchyk.fitflow.ui.screen.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.common.ext.date.toStartOfDay
import com.pichurchyk.fitflow.ui.common.Calendar
import com.pichurchyk.fitflow.ui.common.CustomSnackbar
import com.pichurchyk.fitflow.ui.common.ErrorBottomSheet
import com.pichurchyk.fitflow.ui.common.Header
import com.pichurchyk.fitflow.ui.common.Loader
import com.pichurchyk.fitflow.ui.screen.dashboard.components.AddIntakeFab
import com.pichurchyk.fitflow.ui.screen.dashboard.components.IntakesBlock
import com.pichurchyk.fitflow.ui.screen.dashboard.components.WaterBlock
import com.pichurchyk.fitflow.viewmodel.dashboard.DashboardIntent
import com.pichurchyk.fitflow.viewmodel.dashboard.DashboardViewModel
import com.pichurchyk.fitflow.viewmodel.dashboard.DashboardViewState
import org.koin.androidx.compose.koinViewModel
import java.util.Date

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel()
) {
    val viewState by viewModel.state.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState(initial = Date().toStartOfDay())

    val snackbarHostState = remember { SnackbarHostState() }

    var isCalendarVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(DashboardIntent.LoadData)
    }

    Scaffold(
        modifier = Modifier.safeContentPadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) { data -> CustomSnackbar(data) } },
        content = { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Header(
                        modifier = Modifier,
                        title = stringResource(R.string.app_name),
                    )

                    DateSelector(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(),
                        date = selectedDate,
                        onDateClick = { isCalendarVisible = true },
                        onNextClick = { viewModel.handleIntent(DashboardIntent.SelectNextDate) },
                        onPreviousClick = { viewModel.handleIntent(DashboardIntent.SelectPreviousDate) }
                    )

                    when (val state = viewState) {
                        is DashboardViewState.Error -> {
                            val errorMessage = state.message
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
                            MainContent(
                                modifier = Modifier,
                                state = state,
                                isCalendarVisible = isCalendarVisible,
                                onDateSelected = {
                                    viewModel.handleIntent(
                                        DashboardIntent.ChangeDate(
                                            it
                                        )
                                    )

                                    isCalendarVisible = false
                                },
                                onCalendarClosed = { isCalendarVisible = false }
                            )
                        }
                    }
                }

                AddIntakeFab(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    selectedDate = selectedDate
                )
            }
        }
    )
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    isCalendarVisible: Boolean,
    onDateSelected: (Date) -> Unit,
    onCalendarClosed: () -> Unit,
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
                IntakesBlock(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 40.dp),
                    intakes = state.data.intakes,
                )

                WaterBlock(
                    modifier = Modifier,
                    intakes = state.waterIntakes
                )

                if (isCalendarVisible) {
                    Calendar(
                        modifier = Modifier,
                        onDateSelected = onDateSelected,
                        onDismiss = onCalendarClosed,
                        selectedDate = state.data.date
                    )
                }
            }
        }
    }
}