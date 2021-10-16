package com.ken0105.`interface`

import com.ken0105.app.TodoItem
import com.ken0105.app.TodoService

class TodoController(private val todoService: TodoService) {
    fun getAllTodos(): List<TodoItem> {
        return todoService.getAllTodos()
    }

    fun updateTodo(todoItem: TodoItem) {
        todoService.updateTodo(todoItem)
    }

    fun registerTodo(todo: String) {
        todoService.registerTodo(todo)
    }

    fun deleteTodo(todoId: Int) {
        todoService.deleteTodo(todoId)
    }
}