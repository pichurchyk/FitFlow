package com.pichurchyk.fitflow.ui.screen.addintake

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.common.CommonButton
import com.pichurchyk.fitflow.ui.common.CustomSnackbar
import com.pichurchyk.fitflow.ui.common.Header
import com.pichurchyk.fitflow.ui.common.SnackbarInfo
import com.pichurchyk.fitflow.ui.ext.clearFocusOnClick
import com.pichurchyk.fitflow.ui.ext.getText
import com.pichurchyk.fitflow.viewmodel.addintake.AddIntakeIntent
import com.pichurchyk.fitflow.viewmodel.addintake.AddIntakeViewModel
import com.pichurchyk.fitflow.viewmodel.addintake.AddIntakeViewState
import com.pichurchyk.nutrition.database.model.IntakeType
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Date

@Composable
fun AddIntakeScreen(
    selectedDate: Date,
    viewModel: AddIntakeViewModel = koinViewModel {
        parametersOf(selectedDate)
    },
    closeScreen: () -> Unit,
) {
    val viewState by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    BackHandler {
        closeScreen()
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            coroutineScope.launch {
                viewModel.handleIntent(AddIntakeIntent.CloseError)

                snackbarHostState.showSnackbar(
                    SnackbarInfo(
                        isError = true,
                        message = message
                    )
                )
            }
        }
    }

    var isSuccessVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(isSuccessVisible) {
        if (isSuccessVisible) {
            isSuccessVisible = false
            viewModel.handleIntent(AddIntakeIntent.Reset)
        }
    }

    Scaffold(
        modifier = Modifier.clearFocusOnClick(),
        snackbarHost = { SnackbarHost(snackbarHostState) { data -> CustomSnackbar(data) } },
        topBar = {
            Header(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.add_intake),
                onBackPressed = {
                    closeScreen()
                    viewModel.handleIntent(AddIntakeIntent.Reset)
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = paddingValues.calculateBottomPadding(),
                        top = paddingValues.calculateTopPadding() + 12.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                IntakeInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    intakeType = IntakeType.CARBS,
                    value = viewState.getValuesSumOfType(IntakeType.CARBS),
                    onValueChanged = { newValue ->
                        viewModel.handleIntent(
                            AddIntakeIntent.ChangeIntakeValue(
                                value = newValue,
                                intakeType = IntakeType.CARBS
                            )
                        )
                    },
                    needBottomRadius = false,
                    needTopRadius = true
                )

                IntakeInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    intakeType = IntakeType.PROTEIN,
                    value = viewState.getValuesSumOfType(IntakeType.PROTEIN),
                    onValueChanged = { newValue ->
                        viewModel.handleIntent(
                            AddIntakeIntent.ChangeIntakeValue(
                                value = newValue,
                                intakeType = IntakeType.PROTEIN
                            )
                        )
                    },
                    needBottomRadius = false,
                    needTopRadius = false
                )

                IntakeInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    intakeType = IntakeType.FAT,
                    value = viewState.getValuesSumOfType(IntakeType.FAT),
                    onValueChanged = { newValue ->
                        viewModel.handleIntent(
                            AddIntakeIntent.ChangeIntakeValue(
                                value = newValue,
                                intakeType = IntakeType.FAT
                            )
                        )
                    },
                    needBottomRadius = true,
                    needTopRadius = false
                )

                IntakeInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    intakeType = IntakeType.CALORIES,
                    value = viewState.intake.calories,
                    onValueChanged = { newValue ->
                        viewModel.handleIntent(
                            AddIntakeIntent.ChangeCaloriesValue(
                                value = newValue,
                            )
                        )
                    },
                    needBottomRadius = true,
                    needTopRadius = false
                )

                when (val state = viewState) {
                    is AddIntakeViewState.ValidationException -> {
                        errorMessage = stringResource(state.validationException.getText())
                    }

                    is AddIntakeViewState.Error -> {
                        errorMessage = state.errorMessage
                    }

                    is AddIntakeViewState.Success -> {
                        isSuccessVisible = true
                    }

                    else -> {
                        errorMessage = null
                    }
                }
            }
        },
        bottomBar = {
            CommonButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(id = R.string.submit)
            ) {
                viewModel.handleIntent(AddIntakeIntent.Submit)
            }
        }
    )
}