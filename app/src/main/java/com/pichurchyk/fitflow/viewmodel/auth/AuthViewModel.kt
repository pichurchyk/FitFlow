package com.pichurchyk.fitflow.viewmodel.auth

import cafe.adriel.voyager.core.model.screenModelScope
import com.google.firebase.auth.AuthCredential
import com.pichurchyk.fitflow.auth.model.SignInResult
import com.pichurchyk.fitflow.auth.usecase.CheckIsUserAuthenticatedUseCase
import com.pichurchyk.fitflow.auth.usecase.SignInUseCase
import com.pichurchyk.fitflow.viewmodel.base.BaseScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val checkIsUserAuthenticatedUseCase: CheckIsUserAuthenticatedUseCase,
    private val signInUseCase: SignInUseCase
) : BaseScreenModel() {

    private val _state = MutableStateFlow<AuthViewState>(AuthViewState.Init)
    val state = _state.asStateFlow()

    init {
        screenModelScope.launch {
            checkIsUserAuthenticatedUseCase.invoke()
                .onStart {
                    _state.update { AuthViewState.Loading }
                }
                .catch {
                    _state.update { AuthViewState.Init }
                }
                .collect { isAuthenticated ->
                    if (isAuthenticated) {
                        _state.update { AuthViewState.Success }
                    } else {
                        _state.update { AuthViewState.Init }
                    }
                }
        }
    }

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
        screenModelScope.launch {
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