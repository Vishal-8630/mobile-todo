package com.example.to_doapplication.ui.feature.authentication.screens.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginFields {
    private val _fieldState = MutableStateFlow(LoginFieldState())
    val fieldState: StateFlow<LoginFieldState> = _fieldState

    fun onUsernameChange(username: String) {
        _fieldState.value = _fieldState.value.copy(
            username = username,
            usernameError = null
        )
    }

    fun onPasswordChange(password: String) {
        _fieldState.value = _fieldState.value.copy(
            password = password,
            passwordError = null
        )
    }

    fun setUsernameError(error: String?) {
        _fieldState.value = _fieldState.value.copy(usernameError = error)
    }

    fun setPasswordError(error: String?) {
        _fieldState.value = _fieldState.value.copy(passwordError = error)
    }

    fun clearLoginFields() {
        _fieldState.value = LoginFieldState()
    }
}

data class LoginFieldState(
    val username: String = "",
    val password: String = "",
    val usernameError: String? = null,
    val passwordError: String? = null
)