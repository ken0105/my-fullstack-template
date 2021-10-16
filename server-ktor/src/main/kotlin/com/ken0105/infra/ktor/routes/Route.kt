package com.ken0105.infra.ktor.routes

import com.ken0105.`interface`.TodoController
import com.ken0105.app.TodoItem
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Routing.route() {
    val todoController: TodoController by inject()
    get("/todoItems") {
        call.respond(todoController.getAllTodos())
    }

    put("/todoItems/{todoId}") {
        val todo = call.receive<TodoItem>()
        call.respond(todoController.updateTodo(todo))
    }

    post("/todoItems") {
        val task = call.receive<String>()
        call.respond(todoController.registerTodo(task))
    }

    delete("/todoItems/{todoId}"){
        println("delete is called")
        val todoId = checkNotNull(call.parameters["todoId"]).toInt()
        call.respond(todoController.deleteTodo(todoId))
    }
}

