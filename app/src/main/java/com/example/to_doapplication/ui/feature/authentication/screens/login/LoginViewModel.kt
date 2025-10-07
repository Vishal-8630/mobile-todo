package com.example.to_doapplication.ui.feature.authentication.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_doapplication.repository.AuthRepository
import com.example.to_doapplication.ui.feature.authentication.AuthState
import com.example.to_doapplication.ui.feature.authentication.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    val loginFields = LoginFields()
    val loginFieldState: StateFlow<LoginFieldState> = loginFields.fieldState

    fun validateUsername(username: String): String? {
        return if(username.isEmpty()) "Username cannot be empty" else null
    }

    fun validatePassword(password: String): String? {
        return if(password.isEmpty()) "Password cannot be empty" else null
    }

    fun loginUser() {
        val currentState = loginFieldState.value
        val usernameError = validateUsername(currentState.username.trim())
        val passwordError = validatePassword(currentState.password.trim())

        loginFields.setUsernameError(usernameError)
        loginFields.setPasswordError(passwordError)

        if(usernameError != null || passwordError != null) return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(AuthState.Loading)
            authRepository.loginUser(currentState.username, currentState.password) { success, message ->
                if(success) {
                    authRepository.getCurrentUser { user ->
                        _uiState.value = AuthUiState(
                            status = AuthState.Success("Login Successfully"),
                            user = user
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(status = AuthState.Error(message ?: "Login Failed"))
                }
            }
        }
    }

    fun setCurrentUser() {
        viewModelScope.launch {
            authRepository.getCurrentUser { user ->
                _uiState.value = _uiState.value.copy(user = user)
            }
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = AuthState.Loading)
            authRepository.logoutUser()
            _uiState.value = _uiState.value.copy(status = AuthState.LoggedOut)
        }
    }

    fun resetAuthState() {
        _uiState.value = AuthUiState(
            status = AuthState.Idle,
            user = null
        )
    }

    fun sendOtp() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = AuthState.Loading)
            authRepository.sendOtpToEmail { success, message ->
                if (success) {
                    _uiState.value = _uiState.value.copy(status = AuthState.OtpSend)
                } else {
                    _uiState.value = _uiState.value.copy(status = AuthState.Error(message ?: "Failed to send OTP"))
                }
            }
        }
    }

    fun verifyOtp(enteredOtp: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = AuthState.Loading)
            authRepository.verifyOtp(enteredOtp) { success, message ->
                if (success) {
                    _uiState.value = _uiState.value.copy(status = AuthState.OtpVerified)
                } else {
                    _uiState.value = _uiState.value.copy(status = AuthState.Error(message ?: "Failed to verify OTP"))
                }
            }
        }
    }
}