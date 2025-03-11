package com.pichurchyk.fitflow.viewmodel.splash

import androidx.lifecycle.viewModelScope
import com.pichurchyk.fitflow.auth.usecase.GetSignedInUserUseCase
import com.pichurchyk.fitflow.viewmodel.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getSignedInUser: GetSignedInUserUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow<SplashViewState>(SplashViewState.Loading)
    val state = _state.asStateFlow()

    fun handleIntent(intent: SplashIntent) {
        when (intent) {
            is SplashIntent.CheckIfUserSignedIn -> {
                checkIfUserSignedIn()
            }
        }
    }

    private fun checkIfUserSignedIn() {
        viewModelScope.launch {
            getSignedInUser.invoke()
                .catch {
                    _state.update { SplashViewState.UserNotSignedIn }
                }
                .collect { signedInUser ->
                    if (signedInUser != null) {
                        _state.update { SplashViewState.UserSignedIn }
                    } else {
                        _state.update { SplashViewState.UserNotSignedIn }
                    }
                }
        }
    }
}