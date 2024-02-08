package inu.thebite.tory.screens.setting.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassRequest
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.repositories.ChildClass.ChildClassRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class ChildClassViewModel : ViewModel() {
    private val repo: ChildClassRepoImpl = ChildClassRepoImpl()

    private val _allChildClasses: MutableStateFlow<List<ChildClassResponse>?> = MutableStateFlow(null)
    val allChildClasses = _allChildClasses.asStateFlow()

    private val _childClasses: MutableStateFlow<List<ChildClassResponse>?> = MutableStateFlow(null)
    val childClasses = _childClasses.asStateFlow()

    private val _selectedChildClass = MutableStateFlow<ChildClassResponse?>(null)
    val selectedChildClass = _selectedChildClass.asStateFlow()

    fun setSelectedChildClass(childClassEntity: ChildClassResponse) {

        _selectedChildClass.value = childClassEntity
    }

    fun clearSelectedChildClass() {
        _selectedChildClass.update { null }
    }

    fun clearAllChildClasses(){
        _allChildClasses.update { null }
    }

    fun clearAll(){
        _allChildClasses.update { null }
        _childClasses.update { null }
        _selectedChildClass.update { null }
    }
    init {
//        getAllChildClasses()
        observeAllChildClasses()
    }

    private fun observeAllChildClasses() {
        viewModelScope.launch {

            allChildClasses.onEach { allChildClasses ->
                updateChildClassesAndSelectedChildClass(allChildClasses)
            }.collect()
        }
    }

    private fun updateChildClassesAndSelectedChildClass(allChildClasses: List<ChildClassResponse>?) {
        allChildClasses?.let { allChildClasses ->
            _childClasses.update {currentChildClasses ->
                allChildClasses.filter {childClass ->
                    currentChildClasses?.map { it.id }?.contains(childClass.id) == true
                }
            }
            _selectedChildClass.update {
                val foundChildClass = allChildClasses.find {childClass ->

                    childClass.id == selectedChildClass.value?.id
                }
                foundChildClass
            }
        }
    }

    fun getAllChildClasses(centerId : Long){
        viewModelScope.launch{
            try {
                val allChildClasses = repo.getAllChildClasses(centerId = centerId)
                Log.d("gottenChildClasses", allChildClasses.toString())
                _allChildClasses.update {
                    allChildClasses
                }
            } catch (e: Exception) {
                Log.e("failed to get all child classes", e.message.toString())
            }
        }
    }

    fun getChildClassesByCenter(
        selectedCenter: CenterResponse,
    ){
        if(selectedCenter.isNotNull()){
            _childClasses.update {
                val filteredChildClasses = allChildClasses.value!!.filter {
                    it.center.id == selectedCenter.id
                }
                filteredChildClasses
            }
        }else{
            _childClasses.update { null }
        }
    }

    fun createChildClass(
        selectedCenter: CenterResponse,
        newChildClass: ChildClassRequest
    ) {
        viewModelScope.launch {
            try {
                val response = repo.createChildClass(selectedCenter = selectedCenter, childClass = newChildClass)
                if (response.isSuccessful) {
                    val newChildClassResponse = response.body() ?: throw Exception("ChildClass 정보가 비어있습니다.")
                    Log.d("createChildClass", newChildClassResponse.toString())

                    _allChildClasses.update { currentChildClasses ->
                        currentChildClasses?.let {
                            // 현재 ChildClass 리스트가 null이 아니면 새 ChildClass 추가
                            it + newChildClassResponse
                        } ?: listOf(newChildClassResponse) // 현재 ChildClass 리스트가 null이면 새 리스트를 생성
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("ChildClass 추가 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to add ChildClass", e.message.toString())
            }
        }

    }

    fun updateChildClass(selectedChildClass: ChildClassResponse, updateChildClass: ChildClassRequest) {
        viewModelScope.launch {
            try {
                val response = repo.updateChildClass(selectedChildClass = selectedChildClass, updateChildClass = updateChildClass)

                if (response.isSuccessful) {
                    val updatedChildClass = response.body() ?: throw Exception("ChildClass 정보가 비어있습니다.")
                    _allChildClasses.update {
                        allChildClasses.value?.let { allChildClasses ->
                            allChildClasses.map { if (it.id == updatedChildClass.id) updatedChildClass else it }
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("ChildClass 업데이트 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to update ChildClass", e.message.toString())
            }
        }

    }

    fun deleteChildClass(
        selectedChildClass: ChildClassResponse
    ) {
        viewModelScope.launch {
            try {
                val response = repo.deleteChildClass(childClass = selectedChildClass)

                if (response.isSuccessful) {
                    val isDeleted = response.body() ?: throw Exception("Class 삭제 정보가 비어있습니다.")
                    if (isDeleted) {
                        _allChildClasses.update { currentClasses ->
                            currentClasses?.filterNot { it.id == selectedChildClass.id }
                        }
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("Class 식제 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to delete Class", e.message.toString())
            }
        }
    }
}