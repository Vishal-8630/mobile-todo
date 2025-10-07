package com.example.to_doapplication.repository

import android.util.Log
import com.example.to_doapplication.model.User
import com.example.to_doapplication.util.sendOtpViaSendGrid
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    fun registerUser(email: String, password: String, username: String, onResult: (Boolean, String?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val uid = user?.uid ?: ""
                    val userData = hashMapOf(
                        "id" to uid,
                        "username" to username,
                        "email" to email,
                        "isEmailVerified" to false,
                        "createdAt" to FieldValue.serverTimestamp()
                    )

                    firestore.collection("users")
                        .document(uid)
                        .set(userData)
                        .addOnSuccessListener {
                            onResult(true, null)
                        }
                        .addOnFailureListener {
                            onResult(false, it.message)
                        }
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun loginUser(username: String, password: String, onResult: (Boolean, String?) -> Unit) {
        firestore.collection("users")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if(!querySnapshot.isEmpty) {
                    val user = querySnapshot.documents[0]
                    val email = user.getString("email") ?: ""
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful) {
                                onResult(true, null)
                            } else {
                                onResult(false, task.exception?.message)
                            }
                        }
                } else {
                    onResult(false, "User not found")
                }
            }
            .addOnFailureListener {
                onResult(false, it.message)
            }
    }

    fun logoutUser() {
        firebaseAuth.signOut()
    }

    fun getCurrentUser(onResult: (User?) -> Unit) {
        val user = firebaseAuth.currentUser
        if(user != null) {
            firestore.collection("users")
                .document(user.uid)
                .get(Source.SERVER)
                .addOnSuccessListener { querySnapshot ->
                    val userData = querySnapshot.toObject(User::class.java)
                    onResult(userData)
                }
                .addOnFailureListener {
                    onResult(null)
                }
        } else {
            onResult(null)
        }
    }

    fun sendOtpToEmail(onResult: (Boolean, String?) -> Unit) {
        val user = firebaseAuth.currentUser
        if (user == null) {
            onResult(false, "User not logged in")
            return
        }
        val otp = (100000..999999).random().toString()
        val userDoc = firestore.collection("users").document(user.uid)

        val otpData = mapOf(
            "otp" to otp,
            "otpTimestamp" to System.currentTimeMillis()
        )
        val email = user.email
        if (email.isNullOrEmpty()) {
            onResult(false, "User email is null or empty")
            return
        }
        userDoc.update(otpData)
            .addOnSuccessListener {
                if (email.contains("test", ignoreCase = true)) {
                    onResult(true, null)
                } else {
                    sendOtpViaSendGrid(email, otp) { success, error ->
                        if (success) {
                            onResult(true, null)
                        } else {
                            onResult(false, error)
                        }
                    }
                }
            }
            .addOnFailureListener {
                onResult(false, it.message)
            }
    }

    fun verifyOtp(enteredOtp: String, onResult: (Boolean, String?) -> Unit) {
        val user = firebaseAuth.currentUser
        if (user == null) {
            onResult(false, "User not logged in")
            return
        }
        val userDoc = firestore.collection("users").document(user.uid)
        userDoc.get()
            .addOnSuccessListener { querySnapshot ->
                val storedOtp = querySnapshot.getString("otp")
                val otpTimestamp = querySnapshot.getLong("otpTimestamp") ?: 0L
                val isExpired = System.currentTimeMillis() - otpTimestamp > 5 * 60 * 1000

                if (isExpired) {
                    onResult(false, "OTP has expired")
                } else if (storedOtp == enteredOtp) {
                    userDoc.update("isEmailVerified", true)
                    onResult(true, null)
                } else {
                    onResult(false, "Invalid OTP")
                }
            }
            .addOnFailureListener {
                onResult(false, it.message)
            }
    }

    suspend fun getAllUsers(): List<User> {
        return try {
            val snapshot = firestore.collection("users").get().await()
            snapshot.toObjects(User::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}