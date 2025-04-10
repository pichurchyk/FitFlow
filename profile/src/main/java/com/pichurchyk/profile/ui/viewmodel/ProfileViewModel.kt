package com.pichurchyk.profile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pichurchyk.fitflow.auth.usecase.GetSignedInUserUseCase
import com.pichurchyk.nutrition.model.goals.NutritionGoal
import com.pichurchyk.nutrition.usecase.GetUserGoalsUseCase
import com.pichurchyk.nutrition.usecase.UpdateUserGoalsUseCase
import com.pichurchyk.profile.domain.usecase.GetUserParamsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserUseCase: GetSignedInUserUseCase,
    private val getUserGoalsUseCase: GetUserGoalsUseCase,
    private val getUserParamsUseCase: GetUserParamsUseCase,
    private val updateUserGoalsUseCase: UpdateUserGoalsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileViewState>(ProfileViewState.Loading)
    val state = _state.asStateFlow()

    fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadInfo -> loadUserInfo()
            is ProfileIntent.ChangeNutritionGoal -> {
                changeNutritionGoals(intent.goal)
            }

            is ProfileIntent.SaveChangedNutritionGoals -> {
                saveChangedNutritionGoals()
            }

            is ProfileIntent.DiscardChangedNutritionGoals -> {
                discardChangedNutritionGoals()
            }
        }
    }

    private fun changeNutritionGoals(goal: NutritionGoal) {
        if (goal.value <= 9999) {
            _state.update {
                val currentState = it as? ProfileViewState.Loaded ?: return

                val currentGoalsState = currentState.nutritionGoals
                val updatedGoals = when (currentGoalsState) {
                    is NutritionGoalsViewState.Changing -> {
                        currentGoalsState.newValues.map { existingGoal ->
                            if (existingGoal.id == goal.id) goal else existingGoal
                        }
                    }

                    else -> {
                        currentGoalsState.values.map { existingGoal ->
                            if (existingGoal.id == goal.id) goal else existingGoal
                        }
                    }
                }
                if (currentGoalsState is NutritionGoalsViewState.Changing) {
                    currentState.copy(nutritionGoals = currentGoalsState.copy(newValues = updatedGoals))
                } else {
                    currentState.copy(
                        nutritionGoals = NutritionGoalsViewState.Changing(
                            newValues = updatedGoals,
                            values = currentGoalsState.values
                        )
                    )
                }
            }
        }
    }


    private fun discardChangedNutritionGoals() {
        _state.update {
            val currentState = it as ProfileViewState.Loaded

            val currentGoalsState = currentState.nutritionGoals as NutritionGoalsViewState.Changing

            currentState.copy(nutritionGoals = NutritionGoalsViewState.Loaded(currentGoalsState.values))
        }
    }

    private fun saveChangedNutritionGoals() {
        val currentState = state.value as ProfileViewState.Loaded

        val currentGoalsState = currentState.nutritionGoals as NutritionGoalsViewState.Changing
        val localUpdatedGoals = currentGoalsState.newValues

        viewModelScope.launch {
            updateUserGoalsUseCase
                .invoke(localUpdatedGoals)
                .onStart {
                    _state.update {
                        currentState.copy(
                            nutritionGoals = NutritionGoalsViewState.Loading(
                                localUpdatedGoals
                            )
                        )
                    }
                }
                .catch { error ->
                    _state.update {
                        currentState.copy(
                            nutritionGoals = NutritionGoalsViewState.Error(
                                currentGoalsState.newValues,
                                message = error.message ?: "Some Error Occurred"
                            )
                        )
                    }
                }
                .collect { updatedGoals ->
                    _state.update {
                        currentState.copy(
                            nutritionGoals = NutritionGoalsViewState.Loaded(
                                updatedGoals
                            )
                        )
                    }
                }
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            getUserUseCase.invoke()
                .onStart {
                    _state.update { ProfileViewState.Loading }
                }
                .catch { error ->
                    _state.update {
                        ProfileViewState.Error(
                            error.localizedMessage ?: "Some error occurred"
                        )
                    }
                }
                .collect { user ->
                    user?.let {
                        _state.update { ProfileViewState.Loaded(user) }

                        loadUserGoals()
                        loadUserParams()
                    } ?: kotlin.run {
                        _state.update { ProfileViewState.Error("User not found") }
                    }
                }
        }
    }

    private suspend fun loadUserGoals() {
        getUserGoalsUseCase.invoke()
            .onStart {
                _state.update {
                    val currentGoalsState = (it as ProfileViewState.Loaded).nutritionGoals
                    it.copy(nutritionGoals = NutritionGoalsViewState.Loading(currentGoalsState.values))
                }
            }
            .catch { error ->
                _state.update {
                    val currentGoalsState = (it as ProfileViewState.Loaded).nutritionGoals
                    it.copy(
                        nutritionGoals = NutritionGoalsViewState.Error(
                            currentGoalsState.values,
                            error.message ?: "Some Error Occurred"
                        )
                    )
                }
            }
            .collect { goals ->
                _state.update {
                    (it as ProfileViewState.Loaded).copy(
                        nutritionGoals = NutritionGoalsViewState.Loaded(
                            goals
                        )
                    )
                }
            }

    }

    private suspend fun loadUserParams() {
        getUserParamsUseCase.invoke()
            .catch { error ->
                _state.update {
                    ProfileViewState.Error(
                        error.localizedMessage ?: "Some error occurred"
                    )
                }
            }
            .collect { params ->
                _state.update { currentState ->
                    (currentState as ProfileViewState.Loaded).copy(userParams = params)
                }
            }
    }
}