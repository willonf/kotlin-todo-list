package com.example.intro.repository

import com.example.intro.datasource.FirestoreDataSource
import com.example.intro.model.TaskModel
import kotlinx.coroutines.flow.Flow

class TaskRepository {
    private val firestoreDataSource: FirestoreDataSource = FirestoreDataSource()

    fun saveTask(task: TaskModel) {
        firestoreDataSource.saveTask(task)
    }

    fun getTasks(): Flow<MutableList<TaskModel>> {
        return firestoreDataSource.getTasks()
    }
}