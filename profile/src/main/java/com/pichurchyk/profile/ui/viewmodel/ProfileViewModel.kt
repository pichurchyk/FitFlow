package com.pichurchyk.profile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pichurchyk.fitflow.auth.usecase.GetSignedInUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserUseCase: GetSignedInUserUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileViewState>(ProfileViewState.Loading)
    val state = _state.asStateFlow()

    fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadInfo -> loadUserInfo()
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            getUserUseCase.invoke()
                .onStart {
                    _state.update { ProfileViewState.Loading }
                }
                .catch { error ->
                    _state.update { ProfileViewState.Error(error.localizedMessage ?: "Some error occurred") }
                }
                .collect { user ->
                    user?.let {
                        _state.update { ProfileViewState.Loaded(user) }
                    } ?: kotlin.run {
                        _state.update { ProfileViewState.Error("User not found") }
                    }
                }
        }
    }
}