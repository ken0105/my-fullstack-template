package com.ken0105.infra.ktor.module

import com.ken0105.`interface`.TodoController
import com.ken0105.app.TodoRepository
import com.ken0105.app.TodoService
import com.ken0105.infra.db.TodoRepositoryImpl
import org.koin.dsl.module

object KoinModuleBuilder {
    fun modules(): List<org.koin.core.module.Module> = listOf(module {
        single { TodoController(get())}
        single { TodoService(get()) }
        single<TodoRepository> { TodoRepositoryImpl() }
    })
}