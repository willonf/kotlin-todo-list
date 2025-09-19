package com.example.intro.datasource

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences.Key
import androidx.datastore.preferences.core.edit
import com.example.intro.dataStore
import com.example.intro.model.TaskModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.Clock


class PreferencesDataStore(private val context: Context) {

    fun <T> getPreference(key: Key<T>): Flow<T?> {
        return context.dataStore.data.map { preferences -> preferences[key] }
    }

    suspend fun <T> setPreferences(key: Key<T>, value: T) {
        context.dataStore.edit { preferences -> preferences[key] = value }
    }

}

class FirestoreDataSource {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionReference = firestore.collection("tarefas")
    private val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)

    private val _allTasks = MutableStateFlow<MutableList<TaskModel>>(mutableListOf())
    private val allTasks: StateFlow<MutableList<TaskModel>> = _allTasks

    fun saveTask(task: TaskModel) {
        val documentReference = collectionReference.document()
        val payload = mapOf(
            "id" to documentReference.id,
            "title" to task.title,
            "description" to task.description,
            "location" to task.location,
            "priority" to task.priority,
            "createdAt" to now.date
        )
        firestore
            .collection("tarefas")
            .document("${now}-${task.title}")
            .set(payload)
            .addOnSuccessListener {
                Log.d(
                    "FIRESTORE",
                    "Documento ${now}-${task.title} salvo com sucesso"
                )
            }
            .addOnFailureListener {
                Log.d(
                    "FIRESTORE",
                    "Erro ao salvar documento ${now}-${task.title}"
                )
            }
            .addOnCanceledListener {
                Log.d(
                    "FIRESTORE",
                    "A ação de salvar o documento foi cancelada"
                )
            }
            .addOnCompleteListener { Log.d("FIRESTORE", "Ação finalizada!!!") }
    }

    fun getTasks(): Flow<MutableList<TaskModel>> {
        val taskList: MutableList<TaskModel> = mutableListOf()
        firestore
            .collection("tarefas")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val task = document.toObject<TaskModel>()
                    Log.d("FIRESTORE", task.title!!)
                    taskList.add(task)
                    _allTasks.value = taskList
                }
            }
            .addOnCompleteListener {
                Log.d(
                    "FIRESTORE",
                    "DOCUMENTOS RECUPERADOS!!"
                )
            }
        return allTasks
    }
}















