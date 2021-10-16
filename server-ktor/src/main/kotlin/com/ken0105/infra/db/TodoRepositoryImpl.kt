package com.ken0105.infra.db

import com.ken0105.app.TodoItem
import com.ken0105.app.TodoRepository
import com.ken0105.tables.Todo.TODO
import org.jooq.impl.DSL

class TodoRepositoryImpl : TodoRepository {
    override fun findAll(): List<TodoItem> {
        var result: List<TodoItem> = listOf()
        dslContext
            .transaction { configuration ->
                result =  DSL.using(configuration)
                    .selectFrom(TODO.name)
                .fetchInto(TODO.recordType)
                .map {
                    TodoItem(
                        id = it.id,
                        task = it.task,
                        isDone = it.isDone
                    )
                }
            }
        return result
    }

    override fun update(todoItem: TodoItem) {
        dslContext
            .transaction { configuration ->
                DSL.using(configuration)
                    .update(TODO)
                    .set(TODO.TASK, todoItem.task)
                    .set(TODO.IS_DONE, todoItem.isDone)
                    .where(TODO.ID.eq(todoItem.id))
                    .execute()
            }
    }

    override fun insert(todoItem: TodoItem) {
        dslContext
            .transaction { configuration ->
                DSL.using(configuration)
                    .insertInto(TODO, TODO.TASK, TODO.IS_DONE)
                    .values(todoItem.task, todoItem.isDone)
                    .execute()
            }
    }

    override fun delete(todoItem: TodoItem) {
        dslContext
            .transaction { configuration ->
                DSL.using(configuration)
                    .deleteFrom(TODO)
                    .where(TODO.ID.eq(todoItem.id))
                    .execute()
            }
    }
}