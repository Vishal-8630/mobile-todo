package com.example.to_doapplication.model

import java.util.UUID

data class AppMessage (
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val durationMillis: Long = 5000L
)