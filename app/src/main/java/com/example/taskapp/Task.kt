package com.example.taskapp

import java.util.Date

data class Task(
    val name: String,
    val dueDate: Date,
    var isDone: Boolean = false
)

