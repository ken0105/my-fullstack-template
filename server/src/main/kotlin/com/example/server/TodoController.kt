package com.example.server

import com.example.server.domain.TodoItem
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TodoController(var todoService: TodoService) {
    @GetMapping("/todoItems")
    fun getAllTodos(): List<TodoItem> {
        return todoService.getAllTodos()
    }

    @PutMapping("/todoItems/{todoId}")
    fun updateTodo(@PathVariable todoId: Int,
                   @RequestBody todoItem: TodoItem) {
        todoService.updateTodo(todoId, todoItem)
    }

    @PostMapping("/todoItems")
    fun registerTodo(@RequestBody todo: String) {
        todoService.registerTodo(todo)
    }

    @DeleteMapping("/todoItems/{todoId}")
    fun delteTodo(@PathVariable todoId: Int) {
        todoService.deleteTodo(todoId)
    }
}