package com.example.intro.repository

import com.example.intro.datasource.FirestoreDataSource
import com.example.intro.model.TaskModel

class TaskRepository {
    private val firestoreDataSource: FirestoreDataSource = FirestoreDataSource()

    fun saveTask(task: TaskModel) {
        firestoreDataSource.saveTask(task)
    }
}