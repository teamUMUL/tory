package inu.thebite.tory.schedule

import android.util.Log
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.todo.TodoListRequest
import inu.thebite.tory.model.todo.TodoResponse
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

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _todoResponse: MutableStateFlow<TodoResponse?> = MutableStateFlow(null)
    val todoResponse = _todoResponse.asStateFlow()

    private val _tempTodoResponse: MutableStateFlow<TodoResponse?> = MutableStateFlow(null)
    val tempTodoResponse = _tempTodoResponse.asStateFlow()

    private val _todoSTOIdList: MutableStateFlow<List<Long>?> = MutableStateFlow(null)
    val todoSTOIdList = _todoSTOIdList.asStateFlow()

    init {
        observeTodoList()
//        observeTempTodoList()
    }
//    private fun observeTempTodoList() {
//        viewModelScope.launch {
//            tempTodoResponse.onEach { tempTodoList ->
//                tempTodoList?.let { updateTodoSTOIdList(it) }
//            }.collect()
//        }
//    }
    private fun observeTodoList() {
        viewModelScope.launch {
            todoResponse.onEach { todoList ->

                todoList?.let {
                    Log.d("updatedTempTodoList", todoList.stoList.toString()+isLoading.value.toString())
                    Log.d("updatedTempTodoList", todoResponse.value!!.stoList.toString()+isLoading.value.toString())

                    updateTempTodoList(it)
                }
            }.collect()
        }
    }
    fun updateTodoSTOIdList(
        tempTodoList: TodoResponse
    ){
        _todoSTOIdList.update {
            tempTodoList.stoList
        }
    }
    fun updateTempTodoList(
        todoList: TodoResponse
    ){
        _tempTodoResponse.update {
            todoList
        }
    }

    fun addTodoList(
        studentId: Long,
        todoListRequest: TodoListRequest
    ){
        viewModelScope.launch {
            try {
                val response = repo.addTodoList(studentId = studentId, todoListRequest = todoListRequest)

                if (response.isSuccessful) {
                    val newTodoResponse = response.body() ?: throw Exception("Todo 정보가 비어있습니다.")
                    _todoResponse.update {
                        newTodoResponse
                    }
                    Log.d("newTodoResponse", newTodoResponse.stoList.toString())

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("Todo 추가 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to add Todo", e.message.toString())
            }
        }
    }

    fun updateTodoList(
        studentId: Long,
        updateTodoList: UpdateTodoList
    ){
        viewModelScope.launch {
            try {

                val response = repo.updateTodoList(studentId = studentId, updateTodoList = updateTodoList)

                if (response.isSuccessful) {
                    val updatedTodoResponse = response.body() ?: throw Exception("Todo 정보가 비어있습니다.")
                    Log.d("updatedTOdoResponseAtUpdate", updatedTodoResponse.stoList.toString())
                    _todoResponse.update {
                        updatedTodoResponse
                    }


                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("Todo 추가 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to add Todo", e.message.toString())
            }

        }
    }

    fun getTodoList(
        studentId: Long
    ){
        viewModelScope.launch {
            _isLoading.update { true }
            try {
                val response = repo.getTodoList(studentId = studentId)

                if (response.isSuccessful) {
                    val gotTodoResponse = response.body() ?: throw Exception("Todo 정보가 비어있습니다.")
                    Log.d("gotTodoResponse", gotTodoResponse.stoList.toString())
                    _todoResponse.update {
                        gotTodoResponse
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("Todo 가져오기 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to get Todo", e.message.toString())
            } finally {
                _isLoading.update { false }
            }
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
        _tempTodoResponse.update { currentSchedule ->
            currentSchedule?.let { schedule ->
                // stoList를 수정 가능한 리스트로 복사
                val mutableStoList = schedule.stoList.toMutableList()

                // 인덱스 범위 확인
                if (fromIndex in mutableStoList.indices && toIndex in mutableStoList.indices) {
                    // 요소 교환
                    val temp = mutableStoList[fromIndex]
                    mutableStoList[fromIndex] = mutableStoList[toIndex]
                    mutableStoList[toIndex] = temp

                    // 변경된 리스트로 TodoResponse 업데이트
                    schedule.copy(stoList = mutableStoList)
                } else {
                    schedule // 범위를 벗어난 경우 변경 없이 현재 스케줄 반환
                }
            }
        }
        Log.d("updatedTOdoResponse", tempTodoResponse.value!!.stoList.toString())

    }
}