package com.example.to_doapplication.ui.feature.todo

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class ToDoFields {
    private val _fieldState = MutableStateFlow(ToDoFieldState())
    val fieldState: StateFlow<ToDoFieldState> = _fieldState

    fun onTitleChange(title: String) {
        _fieldState.value = _fieldState.value.copy(
            title = title,
            titleError = null
        )
    }

    fun onDescriptionChange(description: String) {
        _fieldState.value = _fieldState.value.copy(
            description = description,
            descriptionError = null
        )
    }

    fun setTitleError(error: String?) {
        _fieldState.value = _fieldState.value.copy(titleError = error)
    }

    fun setDescriptionError(error: String?) {
        _fieldState.value = _fieldState.value.copy(descriptionError = error)
    }

    fun clearToDoFields() {
        _fieldState.value = ToDoFieldState()
    }
}

enum class ToDoStatus(val displayName: String) {
    COMPLETED("Completed"),
    IN_PROGRESS("In Progress"),
    INCOMPLETE("Incomplete")
}

data class ToDoFieldState(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val description: String = "",
    val titleError: String? = null,
    val descriptionError: String? = null
)