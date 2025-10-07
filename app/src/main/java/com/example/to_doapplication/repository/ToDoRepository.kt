package com.example.to_doapplication.repository

import com.example.to_doapplication.model.ToDo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToDoRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val collection = firestore.collection("todos")

    suspend fun addToDo(toDo: ToDo) {
        collection.document(toDo.id).set(toDo).await()
    }

    suspend fun updateToDo(toDo: ToDo) {
        collection.document(toDo.id).set(toDo.copy(updatedAt = System.currentTimeMillis())).await()
    }

    suspend fun deleteToDo(toDo: ToDo) {
        collection.document(toDo.id).delete().await()
    }

    suspend fun getToDoById(id: String): ToDo? {
        val snapshot = collection.document(id).get().await()
        return snapshot.toObject(ToDo::class.java)
    }

    suspend fun getAllToDoByUser(userId: String): List<ToDo> {
        val snapshot = collection.whereEqualTo("userId", userId).get().await()
        return snapshot.toObjects(ToDo::class.java)
    }
}