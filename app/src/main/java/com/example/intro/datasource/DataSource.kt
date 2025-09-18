package com.example.intro.datasource

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences.Key
import androidx.datastore.preferences.core.edit
import com.example.intro.dataStore
import com.example.intro.model.TaskModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
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
    private val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)

    fun saveTask(task: TaskModel) {
        val payload = mapOf(
            "title" to task.title,
            "description" to task.description,
            "priority" to task.priority,
            "createdAt" to now.date
        )
        firestore
            .collection("tarefas")
            .document("${now}-${task.title}")
            .set(payload)
            .addOnSuccessListener { Log.d("FIRESTORE", "Documento ${now}-${task.title} salvo com sucesso") }
            .addOnFailureListener { Log.d("FIRESTORE", "Erro ao salvar documento ${now}-${task.title}") }
            .addOnCanceledListener { Log.d("FIRESTORE", "A ação de salvar o documento foi cancelada") }
            .addOnCompleteListener { Log.d("FIRESTORE", "Ação finalizada!!!") }
    }
}















