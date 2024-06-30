package com.pichurchyk.fitflow.ui.screen.addintake

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.common.ConfettiExplosion
import com.pichurchyk.fitflow.ui.common.CustomSnackbar
import com.pichurchyk.fitflow.ui.common.ErrorBottomSheet
import com.pichurchyk.fitflow.ui.ext.clearFocusOnClick
import com.pichurchyk.fitflow.ui.ext.getColor
import com.pichurchyk.fitflow.ui.ext.getText
import com.pichurchyk.fitflow.ui.ext.getTitle
import com.pichurchyk.fitflow.ui.theme.color_disable
import com.pichurchyk.fitflow.viewmodel.addintake.AddIntakeIntent
import com.pichurchyk.fitflow.viewmodel.addintake.AddIntakeViewModel
import com.pichurchyk.fitflow.viewmodel.addintake.AddIntakeViewState
import com.pichurchyk.nutrition.database.model.IntakeType
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
            content = { paddingValues ->
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                bottom = paddingValues.calculateBottomPadding(),
                                top = paddingValues.calculateTopPadding()
                            ),
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            Alignment.CenterVertically
                        )
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
                                }
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

                    if (isSuccessVisible) {
                        ConfettiExplosion(
                            modifier = Modifier,
                            explosionDuration = 1000
                        )
                    }
                }
            },
            bottomBar = {
                val animatedColor by animateColorAsState(
                    targetValue = if (!isSuccessVisible) MaterialTheme.colorScheme.primary else color_disable,
                    animationSpec = infiniteRepeatable(
                        animation = tween(500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(animatedColor)
                        .clickable(enabled = !isSuccessVisible) {
                            viewModel.handleIntent(AddIntakeIntent.Submit)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(20.dp),
                        text = stringResource(id = R.string.submit).uppercase(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        letterSpacing = 2.sp
                    )
                }
            }
        )
    }

    @Composable
    private fun IntakeInput(
        modifier: Modifier = Modifier,
        intakeType: IntakeType,
        value: Int,
        onValueChanged: (Int) -> Unit
    ) {
        val valueToShow = if (value == 0) "" else value.toString()
        val placeholder =
            if (intakeType != IntakeType.FAT && intakeType != IntakeType.CARBS && intakeType != IntakeType.PROTEIN) R.string.zero else R.string.zero_grams

        Column(
            modifier = modifier
        ) {
            Text(
                text = stringResource(id = intakeType.getTitle()),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = intakeType.getColor()
            )

            Spacer(modifier = Modifier.height(2.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = valueToShow,
                onValueChange = { newValue ->
                    newValue.toIntOrNull()?.let { validated ->
                        onValueChanged(validated)
                    }
                },
                placeholder = { Text(text = stringResource(id = placeholder)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
        }
    }
}