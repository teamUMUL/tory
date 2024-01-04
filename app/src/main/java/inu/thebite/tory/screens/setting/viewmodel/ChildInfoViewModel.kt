package inu.thebite.tory.screens.setting.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.model.student.AddStudentRequest
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.model.student.UpdateStudentRequest
import inu.thebite.tory.repositories.ChildInfo.ChildInfoRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class ChildInfoViewModel : ViewModel(){
    private val repo: ChildInfoRepoImpl = ChildInfoRepoImpl()

    private val _allChildInfos: MutableStateFlow<List<StudentResponse>?> = MutableStateFlow(null)
    val allChildInfos = _allChildInfos.asStateFlow()


    private val _childInfos: MutableStateFlow<List<StudentResponse>?> = MutableStateFlow(null)
    val childInfos = _childInfos.asStateFlow()

    private val _selectedChildInfo = MutableStateFlow<StudentResponse?>(null)
    val selectedChildInfo = _selectedChildInfo.asStateFlow()

    fun setSelectedChildInfo(childInfoEntity: StudentResponse) {

        _selectedChildInfo.value = childInfoEntity
    }

    fun clearSelectedChildInfo() {
        _selectedChildInfo.value = null
    }

    fun clearChildInfos(){
        _allChildInfos.value = null
    }

    fun clearAll(){
        _allChildInfos.update { null }
        _childInfos.update { null }
        _selectedChildInfo.update { null }
    }
    init {
        observeAllChildClasses()
    }

    private fun observeAllChildClasses() {
        viewModelScope.launch {

            allChildInfos.onEach { allChildInfos ->
                updateChildInfosAndSelectedChildInfo(allChildInfos)
            }.collect()
        }
    }

    private fun updateChildInfosAndSelectedChildInfo(allChildInfos: List<StudentResponse>?) {
        allChildInfos?.let { allChildInfos ->
            _childInfos.update {currentChildInfos ->
                allChildInfos.filter {childInfo ->
                    currentChildInfos?.map { it.id }?.contains(childInfo.id) == true
                }
            }
            _selectedChildInfo.update {
                val foundChildClass = allChildInfos.find { childInfo ->
                    selectedChildInfo.value?.id == childInfo.id
                }
                foundChildClass
            }
        }
    }

    fun getAllChildInfos(
        classId: Long
    ){

        viewModelScope.launch{
            try {
                val allChildInfos = repo.getAllChildInfos(classId = classId)
                _allChildInfos.update {
                    allChildInfos
                }
            } catch (e: Exception) {
                Log.e("failed to get all students", e.message.toString())
            }
        }
    }

    fun getChildInfosByClass(
        selectedClass: ChildClassResponse,
    ){
        if(selectedClass.isNotNull()){
            _childInfos.update {
                val filteredChildInfos = allChildInfos.value!!.filter {
                    it.childClass.id == selectedClass.id
                }
                Log.d("allChildInfos", filteredChildInfos.toString())
                filteredChildInfos

            }
        }else{
            _childInfos.update { null }
        }
    }

    fun createChildInfo(
        selectedChildClass : ChildClassResponse,
        newChildInfo : AddStudentRequest
    ) {
        viewModelScope.launch {
            try {
                val response = repo.createChildInfo(selectedChildClass = selectedChildClass, newChildInfo = newChildInfo)

                if (response.isSuccessful) {
                    val newChildInfoResponse = response.body() ?: throw Exception("ChildInfo 정보가 비어있습니다.")
                    _allChildInfos.update { currentChildInfos ->
                        currentChildInfos?.let {
                            // 현재 ChildInfo 리스트가 null이 아니면 새 ChildInfo 추가
                            it + newChildInfoResponse
                        } ?: listOf(newChildInfoResponse) // 현재 ChildInfo 리스트가 null이면 새 리스트를 생성
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("ChildInfo 추가 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to add ChildInfo", e.message.toString())
            }
        }
    }

    fun updateChildInfo(selectedChildInfo: StudentResponse, updateChildInfo: UpdateStudentRequest) {
        viewModelScope.launch {
            try {
                val response = repo.updateStudent(childInfo = selectedChildInfo, updateStudentRequest = updateChildInfo)

                if (response.isSuccessful) {
                    val updatedChildInfo = response.body() ?: throw Exception("ChildInfo 정보가 비어있습니다.")
                    _allChildInfos.update {
                        allChildInfos.value?.let { allChildInfos ->
                            allChildInfos.map { if (it.id == updatedChildInfo.id) updatedChildInfo else it }
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("ChildInfo 업데이트 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to update ChildInfo", e.message.toString())
            }
        }

    }

    fun deleteChildInfo(selectedChildInfo: StudentResponse) {
        viewModelScope.launch {
            try {
                val response = repo.deleteChildInfo(childInfo = selectedChildInfo)

                if (response.isSuccessful) {
                    val isDeleted = response.body() ?: throw Exception("Child 삭제 정보가 비어있습니다.")
                    if (isDeleted) {
                        _allChildInfos.update { currentChildren ->
                            currentChildren?.filterNot { it.id == selectedChildInfo.id }
                        }
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("Child 식제 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to delete Child", e.message.toString())
            }
        }
    }



}