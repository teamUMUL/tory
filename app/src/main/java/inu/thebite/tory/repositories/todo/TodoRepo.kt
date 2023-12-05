package inu.thebite.tory.repositories.todo

import inu.thebite.tory.model.sto.StoSummaryResponse
import inu.thebite.tory.model.todo.TodoListRequest
import inu.thebite.tory.model.todo.TodoResponse
import inu.thebite.tory.model.todo.UpdateTodoList
import retrofit2.Response

interface TodoRepo {

    suspend fun addTodoList(studentId: Long, todoListRequest: TodoListRequest): Response<TodoResponse>

    suspend fun updateTodoList(studentId: Long, updateTodoList: UpdateTodoList) : Response<TodoResponse>

    suspend fun getTodoList(studentId: Long) : Response<TodoResponse>
}