package com.ken0105.app

interface TodoRepository {
    fun findAll(): List<TodoItem>
    fun update(todoItem: TodoItem)
    fun insert(todoItem: TodoItem)
    fun delete(todoItem: TodoItem)
}