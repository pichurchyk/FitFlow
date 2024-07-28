package com.pichurchyk.fitflow.ui.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.common.Calendar
import com.pichurchyk.fitflow.ui.common.CustomSnackbar
import com.pichurchyk.fitflow.ui.common.ErrorBottomSheet
import com.pichurchyk.fitflow.ui.common.Loader
import com.pichurchyk.fitflow.ui.screen.addintake.AddIntakeScreen
import com.pichurchyk.fitflow.viewmodel.dashboard.DashboardIntent
import com.pichurchyk.fitflow.viewmodel.dashboard.DashboardViewModel
import com.pichurchyk.fitflow.viewmodel.dashboard.DashboardViewState
import java.util.Date

object DashboardScreen : Screen {
    private fun readResolve(): Any = DashboardScreen

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel: DashboardViewModel = getScreenModel()
        val viewState by viewModel.state.collectAsState()
        val selectedDate by viewModel.selectedDate.collectAsState()

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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Header(
                        modifier = Modifier.fillMaxWidth(),
                        date = selectedDate,
                        onDateClick = { isCalendarVisible = true },
                        onNextDateClick = { viewModel.handleIntent(DashboardIntent.SelectNextDate) },
                        onPreviousDateClick = { viewModel.handleIntent(DashboardIntent.SelectPreviousDate) }
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
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navigator.push(AddIntakeScreen(selectedDate))
                    },
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
                    CaloriesTotal(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp),
                        value = state.data.caloriesSum
                    )

                    IntakesRate(
                        modifier = Modifier.fillMaxWidth(),
                        fat = state.getSummaryFat(),
                        carbs = state.getSummaryCarbs(),
                        protein = state.getSummaryProtein(),
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WaterIntakeRate(
                            modifier = Modifier,
                            value = state.getSummaryWater(),
                            limit = 3000
                        )
                    }

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

    @Composable
    private fun Header(
        modifier: Modifier = Modifier,
        date: Date,
        onDateClick: () -> Unit,
        onNextDateClick: () -> Unit,
        onPreviousDateClick: () -> Unit
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
                modifier = Modifier
                    .padding(top = 10.dp, end = 12.dp),
                date = date,
                onDateClick = onDateClick,
                onNextClick = onNextDateClick,
                onPreviousClick = onPreviousDateClick
            )
        }
    }
}