package com.example.to_doapplication.ui.feature.authentication.screens.register

import android.util.Patterns
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
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    val registerFields = RegisterFields()
    val registerFieldState: StateFlow<RegisterFieldState> = registerFields.fieldState

    fun validateEmail(email: String): String? {
        return if(email.isEmpty()) "Email cannot be empty"
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Enter a valid email"
        else null
    }

    fun validateUsername(username: String): String? {
        return if(username.isEmpty()) "Username cannot be empty" else null
    }

    fun validatePassword(password: String): String? {
        return if(password.isEmpty()) "Password cannot be empty"
        else if(password.length < 6) "Password must be at least 6 characters"
        else null
    }

    fun registerUser() {
        val currentState = registerFieldState.value
        val emailError = validateEmail(currentState.email.trim())
        val usernameError = validateUsername(currentState.username.trim())
        val passwordError = validatePassword(currentState.password.trim())

        registerFields.setEmailError(emailError)
        registerFields.setUsernameError(usernameError)
        registerFields.setPasswordError(passwordError)

        if(emailError != null || usernameError != null || passwordError != null) return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(AuthState.Loading)
            authRepository.registerUser(currentState.email, currentState.password, currentState.username) { success, message ->
                if(success) {
                    _uiState.value = _uiState.value.copy(status = AuthState.Success("User registered successfully"))
                } else {
                    _uiState.value = _uiState.value.copy(status = AuthState.Error(message ?: "Registration Failed"))
                }
            }
        }
    }

    fun resetAuthState() {
        _uiState.value = AuthUiState(
            status = AuthState.Idle,
            user = null
        )
    }
}