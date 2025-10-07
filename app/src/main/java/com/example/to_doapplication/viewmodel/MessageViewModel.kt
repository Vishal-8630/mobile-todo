package com.example.to_doapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_doapplication.model.AppMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MessageViewModel: ViewModel() {
    private val _messages = MutableStateFlow<List<AppMessage>>(emptyList())
    val messages: MutableStateFlow<List<AppMessage>> = _messages

    fun showMessage(message: AppMessage) {
        _messages.value += message
        viewModelScope.launch {
            delay(message.durationMillis)
            dismissMessage(message.id)
        }
    }

    fun dismissMessage(id: String) {
        _messages.value = _messages.value.filterNot { it.id == id }
    }
}