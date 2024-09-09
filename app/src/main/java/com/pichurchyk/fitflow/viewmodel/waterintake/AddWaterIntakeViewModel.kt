package com.pichurchyk.fitflow.viewmodel.waterintake

import androidx.lifecycle.viewModelScope
import com.pichurchyk.fitflow.viewmodel.base.BaseViewModel
import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.database.model.dto.IntakeDTO
import com.pichurchyk.nutrition.database.usecase.SaveIntakeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class AddWaterIntakeViewModel(
    private val addIntakeUseCase: SaveIntakeUseCase,
    private val date: Date
) : BaseViewModel() {

    private val _state = MutableStateFlow<AddWaterIntakeViewState>(
        AddWaterIntakeViewState.Init(IntakeDTO.empty(date, IntakeType.WATER))
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
            AddWaterIntakeViewState.Init(IntakeDTO.empty(date, IntakeType.WATER))
        }
    }

    private fun submit() {
        val state = (state.value as AddWaterIntakeViewState.Init)

        viewModelScope.launch {
            _state.update { currentState ->
                AddWaterIntakeViewState.Loading(value = currentState.value)
            }

            try {
                addIntakeUseCase.invoke(state.value)
                    .catch { e ->
                        throw e
                    }
                    .collect {
                        _state.update {
                            AddWaterIntakeViewState.Success(
                                value = state.value
                            )
                        }
                    }
            } catch (e: Throwable) {
                _state.update { currentState ->
                    AddWaterIntakeViewState.Error(
                        value = currentState.value,
                        errorMessage = e.message ?: "Error occurred"
                    )
                }
            }
        }
    }

    private fun closeError() {
        _state.update { currentState ->
            AddWaterIntakeViewState.Init(currentState.value)
        }
    }

    private fun changeIntakeValue(value: Int) {
        val inputState = (state.value as AddWaterIntakeViewState.Init)
        val updatedIntake = inputState.value.copy(value = value)

        _state.update {
            inputState.copy(value = updatedIntake)
        }
    }

}