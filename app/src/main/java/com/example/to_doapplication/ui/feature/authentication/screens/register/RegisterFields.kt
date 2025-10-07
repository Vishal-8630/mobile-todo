package com.example.to_doapplication.ui.feature.authentication.screens.register

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterFields {
    private val _fieldState = MutableStateFlow(RegisterFieldState())
    val fieldState: StateFlow<RegisterFieldState> = _fieldState

    fun onEmailChange(email: String) {
        _fieldState.value = _fieldState.value.copy(
            email = email,
            emailError = null
        )
    }

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

    fun setEmailError(error: String?) {
        _fieldState.value = _fieldState.value.copy(emailError = error)
    }

    fun setUsernameError(error: String?) {
        _fieldState.value = _fieldState.value.copy(usernameError = error)
    }

    fun setPasswordError(error: String?) {
        _fieldState.value = _fieldState.value.copy(passwordError = error)
    }

    fun clearRegisterFields() {
        _fieldState.value = RegisterFieldState()
    }
}

data class RegisterFieldState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val emailError: String? = null,
    val usernameError: String? = null,
    val passwordError: String? = null
)