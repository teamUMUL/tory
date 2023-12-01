package inu.thebite.tory.repositories.todo

import inu.thebite.tory.model.todo.TodoListRequest
import inu.thebite.tory.model.todo.UpdateTodoList
import java.util.concurrent.Flow

interface TodoRepo {

    suspend fun addTodoList(studentId: Long, todoListRequest: TodoListRequest)

    suspend fun updateTOdoList(studentId: Long, updateTodoList: UpdateTodoList)

    suspend fun getTodoList(studentId: Long) : List<String>
}