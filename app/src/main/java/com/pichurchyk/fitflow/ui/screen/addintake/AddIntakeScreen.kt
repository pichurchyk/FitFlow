package com.pichurchyk.fitflow.ui.screen.addintake

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.common.CommonButton
import com.pichurchyk.fitflow.ui.common.CustomSnackbar
import com.pichurchyk.fitflow.ui.common.ErrorBottomSheet
import com.pichurchyk.fitflow.ui.common.Header
import com.pichurchyk.fitflow.ui.ext.clearFocusOnClick
import com.pichurchyk.fitflow.ui.ext.getText
import com.pichurchyk.fitflow.viewmodel.addintake.AddIntakeIntent
import com.pichurchyk.fitflow.viewmodel.addintake.AddIntakeViewModel
import com.pichurchyk.fitflow.viewmodel.addintake.AddIntakeViewState
import kotlinx.coroutines.delay
import org.koin.core.parameter.parametersOf
import java.util.Date

class AddIntakeScreen(
    private val selectedDate: Date? = Date()
) : Screen {
    private fun readResolve(): Any = AddIntakeScreen()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel: AddIntakeViewModel = getScreenModel(
            parameters = { parametersOf(selectedDate) }
        )
        val viewState by viewModel.state.collectAsState()

        val snackbarHostState = remember { SnackbarHostState() }

        val coroutineScope = rememberCoroutineScope()

        var isSuccessVisible by remember {
            mutableStateOf(false)
        }

        LaunchedEffect(isSuccessVisible) {
            if (isSuccessVisible) {
                delay(1000)

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
                    onBackPressed = { navigator.pop() }
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                bottom = paddingValues.calculateBottomPadding(),
                                top = paddingValues.calculateTopPadding()
                            ),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val state = viewState
                        state.intakes.forEach { intake ->
                            IntakeInput(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                intakeType = intake.type,
                                value = intake.value,
                                onValueChanged = { newValue ->
                                    viewModel.handleIntent(
                                        AddIntakeIntent.ChangeIntakeValue(
                                            value = newValue,
                                            intakeType = intake.type
                                        )
                                    )
                                },
                                needBottomRadius = state.intakes.last() == intake,
                                needTopRadius = state.intakes.first() == intake
                            )
                        }


                        when (state) {
                            is AddIntakeViewState.ValidationException -> {
                                val errorMessage = state.validationException.getText()

                                ErrorBottomSheet(
                                    errorMessage = stringResource(id = errorMessage),
                                    dismissButtonText = stringResource(id = R.string.got_it),
                                    onDismiss = {
                                        viewModel.handleIntent(AddIntakeIntent.CloseError)
                                    }
                                )
                            }

                            is AddIntakeViewState.Error -> {
                                ErrorBottomSheet(
                                    errorMessage = state.errorMessage,
                                    dismissButtonText = stringResource(id = R.string.got_it),
                                    onDismiss = {
                                        viewModel.handleIntent(AddIntakeIntent.CloseError)
                                    }
                                )
                            }

                            is AddIntakeViewState.Success -> {
                                isSuccessVisible = true
                            }

                            else -> {}
                        }
                    }
                }
            },
            bottomBar = {
                CommonButton(modifier = Modifier.fillMaxWidth().padding(16.dp), text = stringResource(id = R.string.submit)) {
                    viewModel.handleIntent(AddIntakeIntent.Submit)
                }
            }
        )
    }
}