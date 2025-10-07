package com.example.to_doapplication.ui.feature.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_doapplication.model.User
import com.example.to_doapplication.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    fun getAllUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            _users.value = authRepository.getAllUsers()
            _isLoading.value = false
        }
    }
}