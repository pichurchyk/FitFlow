package com.pichurchyk.fitflow.viewmodel.waterintake

import androidx.lifecycle.viewModelScope
import com.pichurchyk.fitflow.viewmodel.base.BaseViewModel
import com.pichurchyk.nutrition.model.create.CreateWaterIntakeModel
import com.pichurchyk.nutrition.usecase.SaveWaterIntakeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class AddWaterIntakeViewModel(
    private val addWaterIntakeUseCase: SaveWaterIntakeUseCase,
    private val date: Date
) : BaseViewModel() {

    private val _state = MutableStateFlow<AddWaterIntakeViewState>(
        AddWaterIntakeViewState.Init(CreateWaterIntakeModel())
    )
    val state = _state.asStateFlow()

    fun handleIntent(intent: AddWaterIntakeIntent) {
        when (intent) {
            is AddWaterIntakeIntent.ChangeIntakeValue -> {
                changeIntakeValue(intent.value)
            }

            is AddWaterIntakeIntent.CloseError -> {
                closeError()
            }

            is AddWaterIntakeIntent.Submit -> {
                submit()
            }

            is AddWaterIntakeIntent.Reset -> {
                reset()
            }
        }
    }

    private fun reset() {
        _state.update {
            AddWaterIntakeViewState.Init(CreateWaterIntakeModel())
        }
    }

    private fun submit() {
        val state = (state.value as AddWaterIntakeViewState.Init)

        viewModelScope.launch {
            _state.update { currentState ->
                AddWaterIntakeViewState.Loading(intake = currentState.intake)
            }

            try {
                addWaterIntakeUseCase.invoke(state.intake)
                    .catch { e ->
                        throw e
                    }
                    .collect {
                        _state.update {
                            AddWaterIntakeViewState.Success(
                                intake = state.intake
                            )
                        }
                    }
            } catch (e: Throwable) {
                _state.update { currentState ->
                    AddWaterIntakeViewState.Error(
                        intake = currentState.intake,
                        errorMessage = e.message ?: "Error occurred"
                    )
                }
            }
        }
    }

    private fun closeError() {
        _state.update { currentState ->
            AddWaterIntakeViewState.Init(currentState.intake)
        }
    }

    private fun changeIntakeValue(value: Int) {
        val inputState = (state.value as AddWaterIntakeViewState.Init)
        val updatedIntake = inputState.intake.copy(value = value, date = date)

        _state.update {
            inputState.copy(intake = updatedIntake)
        }
    }

}