package inu.thebite.tory.screens.setting.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassRequest
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.repositories.ChildClass.ChildClassRepo
import inu.thebite.tory.repositories.ChildClass.ChildClassRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import java.lang.Exception

class ChildClassViewModel : ViewModel() {
    private val repo: ChildClassRepoImpl = ChildClassRepoImpl()

    private val _allChildClasses = MutableLiveData<List<ChildClassResponse>>()
    val allChildClasses: LiveData<List<ChildClassResponse>> = _allChildClasses

    private val _childClasses: MutableStateFlow<List<ChildClassResponse>?> = MutableStateFlow(null)
    val childClasses = _childClasses.asStateFlow()

    private val _selectedChildClass = MutableStateFlow<ChildClassResponse?>(null)
    val selectedChildClass = _selectedChildClass.asStateFlow()

    fun setSelectedChildClass(childClassEntity: ChildClassResponse) {

        _selectedChildClass.value = childClassEntity
    }

    fun clearSelectedChildClass() {
        _selectedChildClass.value = null
    }

    fun clearChildClasses(){
        _childClasses.value = null
    }
    init {
        getAllChildClasses()
    }

    fun getAllChildClasses(){
        viewModelScope.launch{
            try {
                val allChildClasses = repo.getAllChildClasses()
                _allChildClasses.value = allChildClasses
            } catch (e: Exception) {
                Log.e("forEach문", e.message.toString())
            }
        }
    }

    fun getChildClassesByCenter(
        selectedCenter: CenterResponse,
    ){
        if(selectedCenter.isNotNull()){
            _childClasses.update {
                val filteredChildClasses = allChildClasses.value!!.filter {
                    it.centerId == selectedCenter.id
                }
                filteredChildClasses
            }
        }else{
            _childClasses.update { null }
        }
    }

    fun createChildClass(
        selectedCenter: CenterResponse,
        childClassName: String
    ) {
        viewModelScope.launch {
            try {
                val newChildClassRequest = ChildClassRequest(
                    name = childClassName
                )
                repo.createChildClass(
                    selectedCenter = selectedCenter,
                    childClass = newChildClassRequest
                )
            } catch(e : Exception) {
                Log.e("addCenter", e.message.toString())
            }
        }

    }

//    fun updateChildClass(updatedChildClassEntity: ChildClassEntity) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repo.updateChildClass(updatedChildClassEntity)
//
//        }
//
//    }

    fun deleteChildClass(
        selectedCenter: CenterResponse,
        selectedChildClass: ChildClassResponse
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteChildClass(
                selectedCenter = selectedCenter,
                childClass = selectedChildClass
            )

        }

    }

//    fun deleteChildClassesByCenterName(centerName: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val childClassesToDelete = allChildClasses.value.filter {
//                it.centerName == centerName
//            }
//
//            childClassesToDelete.forEach { childClassEntity ->
//                repo.deleteChildClass(childClassEntity)
//            }
//
//        }
//    }
}