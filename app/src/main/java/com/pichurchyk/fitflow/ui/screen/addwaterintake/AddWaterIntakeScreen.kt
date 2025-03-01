package com.pichurchyk.fitflow.ui.screen.addwaterintake

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.common.CommonButton
import com.pichurchyk.fitflow.ui.common.Header
import com.pichurchyk.fitflow.ui.common.SnackbarInfo
import com.pichurchyk.fitflow.ui.ext.getText
import com.pichurchyk.fitflow.ui.screen.addintake.IntakeInput
import com.pichurchyk.fitflow.ui.theme.AppTheme
import com.pichurchyk.fitflow.viewmodel.waterintake.AddWaterIntakeIntent
import com.pichurchyk.fitflow.viewmodel.waterintake.AddWaterIntakeViewModel
import com.pichurchyk.fitflow.viewmodel.waterintake.AddWaterIntakeViewState
import com.pichurchyk.nutrition.database.model.IntakeType
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Date

@Composable
fun AddWaterIntakeScreen(
    selectedDate: Date,
    viewModel: AddWaterIntakeViewModel = koinViewModel {
        parametersOf(selectedDate)
    },
    closeScreen: () -> Unit
) {
    val viewState by viewModel.state.collectAsState()

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    var isSuccessVisible by remember {
        mutableStateOf(false)
    }

    val snackbarHostState = remember { SnackbarHostState() }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    BackHandler {
        closeScreen()
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            coroutineScope.launch {
                viewModel.handleIntent(AddWaterIntakeIntent.CloseError)

                snackbarHostState.showSnackbar(
                    SnackbarInfo(
                        isError = true,
                        message = message
                    )
                )
            }
        }
    }

    LaunchedEffect(isSuccessVisible) {
        if (isSuccessVisible) {
            isSuccessVisible = false
            viewModel.handleIntent(AddWaterIntakeIntent.Reset)
        }
    }

    Scaffold(
        topBar = {
            Header(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.add_water_intake),
                onBackPressed = {
                    closeScreen()
                    viewModel.handleIntent(AddWaterIntakeIntent.Reset)
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
                val state = viewState

                IntakeInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    intakeType = IntakeType.WATER,
                    value = state.intake.value,
                    onValueChanged = { newValue ->
                        viewModel.handleIntent(
                            AddWaterIntakeIntent.ChangeIntakeValue(
                                value = newValue,
                                intakeType = IntakeType.WATER
                            )
                        )
                    }
                )

                when (state) {
                    is AddWaterIntakeViewState.ValidationException -> {
                        errorMessage = stringResource(state.validationException.getText())
                    }

                    is AddWaterIntakeViewState.Error -> {
                        errorMessage = state.errorMessage
                    }

                    is AddWaterIntakeViewState.Success -> {
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
                viewModel.handleIntent(AddWaterIntakeIntent.Submit)
            }
        }
    )
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        AddWaterIntakeScreen(
            selectedDate = Date(),
            closeScreen = {}
        )
    }
}