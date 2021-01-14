package com.example.server

import com.example.server.domain.TodoItem
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class TodoRepository(val jdbcTemplate: JdbcTemplate) {
    fun all(): List<TodoItem> {
        return jdbcTemplate.query(
                "select * from todo order by id desc") {
            rs: ResultSet, _: Int ->
            TodoItem(
                    rs.getInt("id"),
                    rs.getString("task"),
                    rs.getBoolean("is_done")
            )
        }
    }

    fun updateTodo(todoId: Int, todoItem: TodoItem) {
        jdbcTemplate.update{ connection ->
            val statement = connection.prepareStatement("""
                update todo set task = ?, is_done = ? where id = ?
            """)
            statement.setString(1, todoItem.task)
            statement.setBoolean(2, todoItem.isDone)
            statement.setInt(3, todoItem.id)
            statement
        }
    }

    fun registerTodo(todo: String) {
        jdbcTemplate.update{ connection ->
            val statement = connection.prepareStatement("""
                insert into todo (task, is_done) values (?, false)
            """)
            statement.setString(1, todo)
            statement
        }
    }

    fun deleteTodo(todoId: Int) {
        jdbcTemplate.update { connection ->
            val statement = connection.prepareStatement("""
                delete from todo where id = ?
            """)
            statement.setInt(1, todoId)
            statement
        }
    }
}