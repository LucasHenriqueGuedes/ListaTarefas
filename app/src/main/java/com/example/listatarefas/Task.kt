package com.example.listatarefas

data class Task(
    val id: Int,
    val name: String,
    val description: String,
    var isCompleted: Boolean = false
)