package com.example.to_doapplication.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class User (
    var id: String = "",
    var username: String = "",
    var email: String = "",
    var profileImageUrl: String? = null,
    @get:PropertyName("isEmailVerified") @set:PropertyName("isEmailVerified")
    var isEmailVerified: Boolean = false,
    var createdAt: Timestamp? = null
)

fun Timestamp?.toReadableString(): String {
    if (this == null) return ""
    val date: Date = this.toDate()
    val formatter = SimpleDateFormat("d MMM yyyy, hh:mm a", Locale.getDefault())
    return formatter.format(date)
}