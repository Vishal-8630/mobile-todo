package com.example.to_doapplication.ui.feature.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_doapplication.model.ToDo
import com.example.to_doapplication.model.User
import com.example.to_doapplication.repository.AuthRepository
import com.example.to_doapplication.repository.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    val toDoFields = ToDoFields()
    val toDoFieldState: StateFlow<ToDoFieldState> = toDoFields.fieldState

    private val _todos = MutableStateFlow<List<ToDo>>(emptyList())
    val todos: StateFlow<List<ToDo>> = _todos

    private val _todo = MutableStateFlow<ToDo?>(null)
    val todo: StateFlow<ToDo?> = _todo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _success = MutableStateFlow<String?>(null)
    val success: StateFlow<String?> = _success

    private var originalTitle: String? = null
    private var originalDescription: String? = null

    fun setOriginalValues(toDo: ToDo) {
        originalTitle = toDo.title
        originalDescription = toDo.description
    }

    init {
        getAllToDoByUser()
    }

    fun getAllToDoByUser() {
        viewModelScope.launch {
            _isLoading.value = true
            val user = suspendCancellableCoroutine<User?> { cont ->
                authRepository.getCurrentUser { fetchedUser ->
                    cont.resume(fetchedUser) { cause, _, _ -> cont.cancel(cause) }
                }
            }
            if (user != null) {
                _todos.value = emptyList()
                try {
                    val toDos = toDoRepository.getAllToDoByUser(user.id)
                    _todos.value = toDos
                } catch (e: Exception) {
                    _error.value = e.message
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun clearSuccess() {
        _success.value = null
    }

    fun saveToDo() {
        viewModelScope.launch {
            val currentState = toDoFieldState.value

            if (currentState.title.trim().isBlank()) {
                toDoFields.setTitleError("Title cannot be empty")
                return@launch
            }

            if (currentState.description.trim().isBlank()) {
                toDoFields.setDescriptionError("Description cannot be empty")
                return@launch
            }
            val user = suspendCancellableCoroutine<User?> { cont ->
                authRepository.getCurrentUser { fetchedUser ->
                    cont.resume(fetchedUser) { cause, _, _ -> cont.cancel(cause) }
                }
            }

            val toDo = ToDo(
                id = currentState.id,
                title = currentState.title,
                description = currentState.description,
                status = ToDoStatus.INCOMPLETE.displayName,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                userId = user?.id ?: ""
            )
            _isLoading.value = true

            try {
                toDoRepository.addToDo(toDo)
                _todos.value = _todos.value + toDo
                toDoFields.clearToDoFields()
                _success.value = "To-Do added successfully"
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateToDo(toDoId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val currentState = toDoFieldState.value
                if(currentState.title.trim().isEmpty()) {
                    toDoFields.setTitleError("Title cannot be empty")
                    return@launch
                }
                if(currentState.description.trim().isEmpty()) {
                    toDoFields.setDescriptionError("Description cannot be empty")
                    return@launch
                }
                val existingToDo = _todo.value
                val updatedToDo = ToDo(
                    id = toDoId,
                    userId = existingToDo?.userId ?: "",
                    title = currentState.title.trim(),
                    description = currentState.description.trim(),
                    status = existingToDo?.status ?: ToDoStatus.INCOMPLETE.displayName,
                    createdAt = existingToDo?.createdAt ?: System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                toDoRepository.updateToDo(updatedToDo)
                _todo.value = updatedToDo
                _success.value = "To-Do updated successfully"
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getToDoById(toDoId: String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _todo.value = toDoRepository.getToDoById(toDoId)
                setOriginalValues(_todo.value!!)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun hasUnsavedChanges(): Boolean {
        val current = toDoFieldState.value
        return current.title.trim() != originalTitle?.trim() ||
                current.description.trim() != originalDescription?.trim()
    }

    fun deleteToDo(toDo: ToDo) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                toDoRepository.deleteToDo(toDo)
                _todos.value = _todos.value - toDo
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateToDoStatus(toDo: ToDo, newStatus: ToDoStatus) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val updatedToDo = toDo.copy(
                    status = newStatus.displayName,
                    updatedAt = System.currentTimeMillis()
                )
                toDoRepository.updateToDo(updatedToDo)
                _todos.value = _todos.value.map {
                    if (it.id == toDo.id) updatedToDo else it
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}