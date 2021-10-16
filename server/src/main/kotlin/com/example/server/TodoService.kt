package com.example.server

import com.example.server.domain.TodoItem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class TodoService(private val todoRepository: TodoRepository) {
//    ↓ is not recommended
//    @Autowired
//    private lateinit var todoRepository: TodoRepository

    fun getAllTodos(): List<TodoItem> {
        return todoRepository.all()
    }

    fun updateTodo(todoId: Int, todoItem: TodoItem) {
        todoRepository.updateTodo(todoId, todoItem)
    }

    fun registerTodo(todo: String) {
        todoRepository.registerTodo(todo)
    }

    fun deleteTodo(todoId: Int) {
        todoRepository.deleteTodo(todoId)
    }
}