package inu.thebite.tory.repositories.todo

import inu.thebite.tory.model.todo.TodoListRequest
import inu.thebite.tory.model.todo.UpdateTodoList
import inu.thebite.tory.retrofit.RetrofitApi

class TodoRepoImpl : TodoRepo {

    private val todoRetrofit = RetrofitApi.apiService

    override suspend fun addTodoList(studentId: Long, todoListRequest: TodoListRequest) {
        todoRetrofit.addTodoList(studentId = studentId, todoListRequest = todoListRequest)
    }

    override suspend fun updateTOdoList(studentId: Long, updateTodoList: UpdateTodoList) {
        todoRetrofit.updateTodoList(studentId = studentId, updateTodoList = updateTodoList)
    }

    override suspend fun getTodoList(studentId: Long): List<String> {
        return todoRetrofit.getTodoList(studentId = studentId)
    }
}