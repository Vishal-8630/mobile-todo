package com.example.to_doapplication.ui.feature.authentication

import com.example.to_doapplication.model.User

sealed class AuthState{
    object Idle: AuthState()
    object Loading: AuthState()
    object LoggedOut: AuthState()
    data class Success(val message: String): AuthState()
    data class Error(val message: String): AuthState()
    object OtpSend: AuthState()
    object OtpVerified: AuthState()
}

data class AuthUiState (
    val status: AuthState = AuthState.Idle,
    val user: User? = null
)