package inu.thebite.tory.repositories.todo

import inu.thebite.tory.model.sto.StoSummaryResponse
import inu.thebite.tory.model.todo.TodoListRequest
import inu.thebite.tory.model.todo.TodoResponse
import inu.thebite.tory.model.todo.UpdateTodoList
import inu.thebite.tory.retrofit.RetrofitApi
import retrofit2.Response

class TodoRepoImpl : TodoRepo {

    private val todoRetrofit = RetrofitApi.apiService

    override suspend fun addTodoList(studentId: Long, todoListRequest: TodoListRequest) : Response<TodoResponse> {
        return todoRetrofit.addTodoList(studentId = studentId, todoListRequest = todoListRequest)
    }

    override suspend fun updateTodoList(studentId: Long, updateTodoList: UpdateTodoList) : Response<TodoResponse> {
         return todoRetrofit.updateTodoList(studentId = studentId, updateTodoList = updateTodoList)
    }

    override suspend fun getTodoList(studentId: Long) : Response<TodoResponse>  {
        return todoRetrofit.getTodoList(studentId = studentId)
    }
}