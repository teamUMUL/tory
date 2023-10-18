package inu.thebite.tory.screens.setting.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.model.student.AddStudentRequest
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.repositories.ChildInfo.ChildInfoRepo
import inu.thebite.tory.repositories.ChildInfo.ChildInfoRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
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
        _childInfos.value = null
    }
    init {
        getAllChildInfos()
    }

    fun getAllChildInfos(){
        viewModelScope.launch{
            try {
                val allChildInfos = repo.getAllChildInfos()
                _allChildInfos.value = allChildInfos
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
                    it.id == selectedClass.id
                }
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
        viewModelScope.launch{
            try {
                repo.createChildInfo(
                    selectedChildClass,
                    newChildInfo
                )
            } catch (e: Exception) {
                Log.e("failed to create student", e.message.toString())
            }
            getAllChildInfos()
        }
    }

//    fun updateChildInfo(updatedChildInfoEntity: ChildInfoEntity) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repo.upda(updatedChildInfoEntity)
//        }
//
//    }

    fun deleteChildInfo(selectedChildInfo: StudentResponse) {
        viewModelScope.launch {
            try {
                repo.deleteChildInfo(selectedChildInfo)
            } catch (e: Exception) {
                Log.e("failed to delete student", e.message.toString())
            }
        }
    }



}