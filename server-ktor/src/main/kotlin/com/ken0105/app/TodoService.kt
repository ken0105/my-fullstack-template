package com.ken0105.app

class TodoService(private val todoRepository: TodoRepository) {
    fun getAllTodos(): List<TodoItem> {
        return todoRepository.findAll()
    }

    fun updateTodo(todoItem: TodoItem) {
        todoRepository.update(todoItem)
    }

    fun registerTodo(todo: String) {
        todoRepository.insert(TodoItem(id = null, task = todo, isDone = false))
    }

    fun deleteTodo(todoId: Int) {
        todoRepository.delete(TodoItem(id = todoId, task = "", isDone = false))
    }
}

data class TodoItem(
    val id: Int?,
    val task: String,
    val isDone: Boolean,
)