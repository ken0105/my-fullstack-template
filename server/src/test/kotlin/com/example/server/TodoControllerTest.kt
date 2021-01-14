package com.example.server

import com.example.server.domain.TodoItem
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = ["/test-clear_db.sql", "/test-seed_db.sql"])
class TodoControllerTest {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun `GET API returns todolist`() {
        val response = restTemplate.exchange("/todoItems", HttpMethod.GET, null, String::class.java)
        val todos = jacksonObjectMapper().readValue<List<TodoItem>>(response.body!!)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(todos).isEqualTo(
                listOf(TodoItem(2, "todo2 for tests", true),
                        TodoItem(1, "todo for tests", false)))
    }

    @Test
    fun `PUT API makes todo done`() {
        val todoChanged = TodoItem(1, "replaced todo for tests", true)
        var response = restTemplate.exchange("/todoItems/1", HttpMethod.PUT, HttpEntity(todoChanged, null), String::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        response = restTemplate.exchange("/todoItems", HttpMethod.GET, null, String::class.java)

        val todos = jacksonObjectMapper().readValue<List<TodoItem>>(response.body!!)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(todos).isEqualTo(
                listOf(TodoItem(2, "todo2 for tests", true),
                        TodoItem(1, "replaced todo for tests", true)))
    }

    @Test
    fun `POST API create a new todo`() {
        val newTodo = "This is a new todo."
        var response = restTemplate.exchange("/todoItems", HttpMethod.POST, HttpEntity(newTodo, null), String::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        response = restTemplate.exchange("/todoItems", HttpMethod.GET, null, String::class.java)

        val todos = jacksonObjectMapper().readValue<List<TodoItem>>(response.body!!)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(todos).isEqualTo(
                listOf(TodoItem(3, "This is a new todo.", false),
                        TodoItem(2, "todo2 for tests", true),
                        TodoItem(1, "todo for tests", false)))
    }

    @Test
    fun `DELETE API delete the specified todo`() {
        var response = restTemplate.exchange("/todoItems/1", HttpMethod.DELETE, null, String::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        response = restTemplate.exchange("/todoItems", HttpMethod.GET, null, String::class.java)

        val todos = jacksonObjectMapper().readValue<List<TodoItem>>(response.body!!)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(todos).isEqualTo(
                listOf(TodoItem(2, "todo2 for tests", true)))
    }

}