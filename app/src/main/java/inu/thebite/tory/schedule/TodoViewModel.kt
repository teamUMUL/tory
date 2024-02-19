package inu.thebite.tory.schedule

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.dmoral.toasty.Toasty
import inu.thebite.tory.model.todo.TodoListRequest
import inu.thebite.tory.model.todo.TodoResponse
import inu.thebite.tory.model.todo.UpdateTodoList
import inu.thebite.tory.repositories.todo.TodoRepoImpl
import inu.thebite.tory.screens.education.compose.sidebar.currentToast
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

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
    fun clearAll(){
        _todoResponse.update { null }
        _tempTodoResponse.update { null }
        _todoSTOIdList.update { null }
    }
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

    fun getTodoList(studentId: Long, context: Context) {
        viewModelScope.launch {
            _isLoading.update { true }
            try {
                // withTimeoutOrNull을 사용하여 5초(5000ms) 동안 작업을 시도합니다.
                val response = withTimeoutOrNull(5000) {
                    repo.getTodoList(studentId = studentId)
                }

                if (response == null) {
                    // 시간 초과로 인해 response가 null인 경우, 로딩 실패 처리
                    Log.e("getTodoList", "Todo 가져오기 시간 초과")
                    // 여기서 사용자에게 가져오기 실패를 알리는 로직을 추가할 수 있습니다.
                    _isLoading.update { false }
                    currentToast?.cancel()
                    val newToast = Toasty.error(context, "To Do 목록 가져오기 실패", Toast.LENGTH_SHORT, true)
                    newToast.show()
                    currentToast = newToast

                } else {
                    // 응답 처리 로직
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
                }
            } catch (e: TimeoutCancellationException) {
                Log.e("getTodoList", "요청 시간 초과: ${e.message}")
                // 요청 시간 초과 처리 로직
            } catch (e: Exception) {
                Log.e("getTodoList", "실패: ${e.message}")
                // 예외 처리 로직
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