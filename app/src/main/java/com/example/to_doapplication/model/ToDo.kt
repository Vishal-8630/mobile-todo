package com.example.to_doapplication.model

import com.example.to_doapplication.ui.feature.todo.ToDoStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class ToDo(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val description: String = "",
    val status: String = ToDoStatus.INCOMPLETE.name,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val userId: String = ""
)

fun Long.toReadableTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return format.format(date)
}