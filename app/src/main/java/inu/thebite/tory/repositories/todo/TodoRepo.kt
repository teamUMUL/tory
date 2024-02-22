package inu.thebite.tory.repositories.todo

import inu.thebite.tory.model.sto.StoSummaryResponse
import inu.thebite.tory.model.todo.RecentTodoWithDateResponse
import inu.thebite.tory.model.todo.TodoListRequest
import inu.thebite.tory.model.todo.TodoResponse
import inu.thebite.tory.model.todo.UpdateTodoList
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoRepo {

    suspend fun addTodoList(studentId: Long, todoListRequest: TodoListRequest): Response<TodoResponse>

    suspend fun updateTodoList(studentId: Long, updateTodoList: UpdateTodoList) : Response<TodoResponse>

    suspend fun getTodoList(studentId: Long) : Response<TodoResponse>

    suspend fun getRecentTodoListWithDate(studentId: Long, startDate: String, endDate: String) : Response<List<RecentTodoWithDateResponse>>

}