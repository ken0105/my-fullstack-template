package com.example.server.domain

data class TodoItem(
        val id: Int,
        val task: String,
        val isDone: Boolean,
)