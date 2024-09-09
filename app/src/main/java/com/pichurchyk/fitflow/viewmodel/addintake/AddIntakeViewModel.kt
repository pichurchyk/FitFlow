package com.pichurchyk.fitflow.viewmodel.addintake

import androidx.lifecycle.viewModelScope
import com.pichurchyk.fitflow.viewmodel.base.BaseViewModel
import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.database.model.dto.IntakeDTO
import com.pichurchyk.nutrition.database.usecase.SaveIntakeUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class AddIntakeViewModel(
    private val addIntakeUseCase: SaveIntakeUseCase,
    private val date: Date
) : BaseViewModel() {

    private val _state = MutableStateFlow<AddIntakeViewState>(
        AddIntakeViewState.Init(
            IntakeDTO.getIntakesDtoByMainTypes(Date())
        )
    )
    val state = _state.asStateFlow()

    fun handleIntent(intent: AddIntakeIntent) {
        when (intent) {
            is AddIntakeIntent.ChangeIntakeValue -> {
                changeIntakeValue(intent.value, intent.intakeType)
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
        _state.update {
            AddIntakeViewState.Init(
                IntakeDTO.getIntakesDtoByMainTypes(Date())
            )
        }
    }

    private fun submit() {
        val isValidData = validate()
        if (!isValidData) return

        val state = (state.value as AddIntakeViewState.Init)

        viewModelScope.launch {
            _state.update { currentState ->
                AddIntakeViewState.Loading(intakes = currentState.intakes)
            }

            val intakes = state.intakes.filterNot { it.value == 0 }

            try {
                intakes.map { intake ->
                    async {
                        addIntakeUseCase.invoke(intake)
                            .catch { e ->
                                throw e
                            }
                            .collect()
                    }
                }.awaitAll()

                _state.update {
                    AddIntakeViewState.Success(
                        IntakeDTO.getIntakesDtoByMainTypes(Date())
                    )
                }
            } catch (e: Throwable) {
                _state.update { currentState ->
                    AddIntakeViewState.Error(
                        intakes = currentState.intakes,
                        errorMessage = e.message ?: "Error occurred"
                    )
                }
            }
        }
    }
    private fun validate(): Boolean {
        val state = (state.value as AddIntakeViewState.Init)

        val caloriesIntakes = state.intakes.firstOrNull { it.type == IntakeType.CALORIES }!!
        val otherIntakes = state.intakes.filterNot { it.type == IntakeType.CALORIES }

        val exceptions = mutableListOf<AddIntakeValidationException>()

        when {
            otherIntakes.all { it.value == 0 } -> exceptions.add(AddIntakeValidationException.EMPTY_VALUES)
            caloriesIntakes.value == 0 -> exceptions.add(AddIntakeValidationException.EMPTY_CALORIES)
        }

        if (exceptions.isNotEmpty()) {
            _state.update {
                AddIntakeViewState.ValidationException(
                    intakes = state.intakes,
                    validationException = exceptions.first()
                )
            }
        }

        return exceptions.isEmpty()
    }

    private fun closeError() {
        _state.update { currentState ->
            AddIntakeViewState.Init(currentState.intakes)
        }
    }

    private fun changeIntakeValue(value: Int, intakeType: IntakeType) {
        val inputState = (state.value as AddIntakeViewState.Init)
        val updatedIntakes = inputState.intakes.map { intake ->
            if (intake.type == intakeType) {
                intake.copy(value = value, date = date)
            } else {
                intake
            }
        }

        _state.update {
            inputState.copy(intakes = updatedIntakes)
        }
    }

}