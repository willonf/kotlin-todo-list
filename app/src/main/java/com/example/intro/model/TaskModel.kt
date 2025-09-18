package com.example.intro.model

enum class Priority(val value: Int) {
    NO_PRIORITY(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3),
}

data class TaskModel(
    val title: String? = null,
    val description: String? = null,
    val priority: Int? = null
)
