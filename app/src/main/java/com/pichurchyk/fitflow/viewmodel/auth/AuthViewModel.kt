package com.pichurchyk.fitflow.viewmodel.auth

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.pichurchyk.fitflow.auth.model.SignInResult
import com.pichurchyk.fitflow.auth.usecase.SignInUseCase
import com.pichurchyk.fitflow.viewmodel.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val signInUseCase: SignInUseCase,
) : BaseViewModel() {

    private val _state = MutableStateFlow<AuthViewState>(AuthViewState.Init)
    val state = _state.asStateFlow()

    fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.Auth -> {
                auth(intent.credentials)
            }

            is AuthIntent.Clear -> {
                _state.update { AuthViewState.Init }
            }
        }
    }

    private fun auth(credentials: AuthCredential) {
        viewModelScope.launch {
            signInUseCase.invoke(credentials)
                .onStart {
                    _state.update { AuthViewState.Loading }
                }
                .catch { error ->
                    _state.update { AuthViewState.Error(error.message ?: defaultError) }
                }
                .collect { response ->
                    when (response) {
                        is SignInResult.Success -> {
                            _state.update { AuthViewState.Success }
                        }

                        is SignInResult.Error -> {
                            _state.update { AuthViewState.Error(response.errorMessage) }
                        }

                        is SignInResult.Cancelled -> {
                            _state.update { AuthViewState.Init }
                        }
                    }
                }
        }
    }

}