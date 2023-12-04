package inu.thebite.tory.schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.model.sto.StoSummaryResponse
import inu.thebite.tory.model.todo.TodoListRequest
import inu.thebite.tory.model.todo.UpdateTodoList
import inu.thebite.tory.repositories.todo.TodoRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {

    private val repo: TodoRepoImpl = TodoRepoImpl()

    private val _todoList: MutableStateFlow<List<StoSummaryResponse>?> = MutableStateFlow(null)
    val todoList = _todoList.asStateFlow()

    private val _tempTodoList: MutableStateFlow<List<StoSummaryResponse>?> = MutableStateFlow(null)
    val tempTodoList = _tempTodoList.asStateFlow()

    init {
        observeTodoList()
    }

    private fun observeTodoList() {
        viewModelScope.launch {
            todoList.onEach { todoList ->
                todoList?.let { updateTempTodoList(it) }
            }.collect()
        }
    }


    fun updateTempTodoList(
        todoList: List<StoSummaryResponse>
    ){
        _tempTodoList.update {
            todoList
        }
    }

    fun addTodoList(
        studentId: Long,
        todoListRequest: TodoListRequest
    ){
        try {
            viewModelScope.launch {
                repo.addTodoList(studentId = studentId, todoListRequest = todoListRequest)
            }
        } catch (e: Exception){
            Log.e("failed to add TodoList", e.message.toString())
        }
    }

    fun updateTodoList(
        studentId: Long,
        updateTodoList: UpdateTodoList
    ){
        try {
            viewModelScope.launch {
                repo.updateTOdoList(studentId = studentId, updateTodoList = updateTodoList)
            }
        } catch (e: Exception){
            Log.e("failed to update TodoList", e.message.toString())
        }
    }

    fun getTodoList(
        studentId: Long
    ){
        try {
            viewModelScope.launch {
                _todoList.update {
                    repo.getTodoList(studentId = studentId)
                }
            }
        } catch (e: Exception){
            Log.e("failed to get TodoList", e.message.toString())
        }
    }


//    fun addTempTodoList(stoEntity: StoResponse){
//        _tempTodoList.update {
//            val beforeSchedule = tempTodoList.value?.let {schedule ->
//                schedule.toMutableList()
//            } ?: mutableListOf()
//            beforeSchedule.add(stoEntity)
//            beforeSchedule
//        }
//    }
//
//    fun deleteTempTodoList(stoEntity: StoResponse){
//        _tempTodoList.update {
//            val beforeSchedule = tempTodoList.value?.let {schedule ->
//                schedule.toMutableList()
//            } ?: mutableListOf()
//            beforeSchedule.remove(stoEntity)
//            beforeSchedule
//        }
//    }

    fun moveTempTodoList(fromIndex: Int, toIndex: Int){
        _tempTodoList.update { currentSchedule ->
            currentSchedule?.let {
                // 새로운 리스트를 만들어 기존의 리스트를 복사합니다.
                val updatedSchedule = it.toMutableList()

                // 안전하게 범위 내의 인덱스인지 확인합니다.
                if (fromIndex in updatedSchedule.indices && toIndex in updatedSchedule.indices) {
                    // fromIndex와 toIndex의 항목을 서로 교환합니다.
                    val temp = updatedSchedule[fromIndex]
                    updatedSchedule[fromIndex] = updatedSchedule[toIndex]
                    updatedSchedule[toIndex] = temp
                }
                updatedSchedule
            }
        }

    }
}