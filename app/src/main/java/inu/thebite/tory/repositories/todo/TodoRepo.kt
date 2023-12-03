package inu.thebite.tory.repositories.todo

import inu.thebite.tory.model.sto.StoSummaryResponse
import inu.thebite.tory.model.todo.TodoListRequest
import inu.thebite.tory.model.todo.UpdateTodoList

interface TodoRepo {

    suspend fun addTodoList(studentId: Long, todoListRequest: TodoListRequest)

    suspend fun updateTOdoList(studentId: Long, updateTodoList: UpdateTodoList)

    suspend fun getTodoList(studentId: Long) : List<StoSummaryResponse>
}