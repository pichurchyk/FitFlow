package com.pichurchyk.fitflow.viewmodel.addintake

import androidx.lifecycle.viewModelScope
import com.pichurchyk.fitflow.viewmodel.base.BaseViewModel
import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.model.create.CreateIntakeModel
import com.pichurchyk.nutrition.model.create.CreateIntakeValueModel
import com.pichurchyk.nutrition.usecase.SaveIntakeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class AddIntakeViewModel(
    private val addIntakeUseCase: SaveIntakeUseCase,
    private val date: Date
) : BaseViewModel() {

    private val _state = MutableStateFlow<AddIntakeViewState>(
        AddIntakeViewState.Init(CreateIntakeModel(date = date))
    )
    val state = _state.asStateFlow()

    fun handleIntent(intent: AddIntakeIntent) {
        when (intent) {
            is AddIntakeIntent.ChangeIntakeValue -> {
                changeIntakeValue(intent.value, intent.intakeType)
            }

            is AddIntakeIntent.ChangeCaloriesValue -> {
                changeCaloriesValue(intent.value)
            }

            is AddIntakeIntent.CloseError -> {
                closeError()
            }

            is AddIntakeIntent.Submit -> {
                submit()
            }

            is AddIntakeIntent.Reset -> {
                reset()
            }
        }
    }

    private fun reset() {
        _state.update { AddIntakeViewState.Init(CreateIntakeModel()) }
    }

    private fun submit() {
        val isValidData = validate()
        if (!isValidData) return

        val state = (state.value as AddIntakeViewState.Init)

        viewModelScope.launch {
            _state.update { currentState ->
                AddIntakeViewState.Loading(intake = currentState.intake)
            }

            try {
                launch {
                    addIntakeUseCase.invoke(state.intake)
                        .catch {
                            throw it
                        }
                        .collect {
                            reset()
                        }
                }
            } catch (e: Throwable) {
                _state.update { currentState ->
                    AddIntakeViewState.Error(
                        intake = currentState.intake,
                        errorMessage = e.message ?: "Error occurred"
                    )
                }
            }
        }
    }

    private fun validate(): Boolean {
        val state = (state.value as AddIntakeViewState.Init)

        val exceptions = mutableListOf<AddIntakeValidationException>()

        when {
            state.intake.values.all { it.value == 0 } -> exceptions.add(AddIntakeValidationException.EMPTY_VALUES)
            state.intake.calories == 0 -> exceptions.add(AddIntakeValidationException.EMPTY_CALORIES)
        }

        if (exceptions.isNotEmpty()) {
            _state.update {
                AddIntakeViewState.ValidationException(
                    intake = state.intake,
                    validationException = exceptions.first()
                )
            }
        }

        return exceptions.isEmpty()
    }

    private fun closeError() {
        _state.update { currentState ->
            AddIntakeViewState.Init(currentState.intake)
        }
    }

    private fun changeIntakeValue(value: Int, intakeType: IntakeType) {
        val currentState = (state.value as AddIntakeViewState.Init)
        val currentIntake = currentState.intake

        val updatedIntakes: List<CreateIntakeValueModel> = if (currentIntake.values.any { it.intakeType == intakeType }) {
            currentIntake.values.map { intake ->
                if (intake.intakeType == intakeType) {
                    intake.copy(value = value)
                } else {
                    intake
                }
            }
        } else {
            currentIntake.values + CreateIntakeValueModel(value = value, intakeType = intakeType)
        }

        _state.update {
            currentState.copy(intake = currentIntake.copy(values = updatedIntakes))
        }

    }

    private fun changeCaloriesValue(value: Int) {
        val currentState = (state.value as AddIntakeViewState.Init)
        val currentIntake = currentState.intake

        _state.update {
            currentState.copy(intake = currentIntake.copy(calories = value))
        }
    }
}