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
                repo.createChildClass(
                    selectedCenter = selectedCenter,
                    childClass = newChildClass
                )
            } catch(e : Exception) {
                Log.e("failed to create child class", e.message.toString())
            }
            getAllChildClasses()
        }

    }

    fun updateChildClass(selectedChildClass: ChildClassResponse, updateChildClass: ChildClassRequest) {
        viewModelScope.launch() {
            try {
                repo.updateChildClass(selectedChildClass, updateChildClass)
            } catch (e: Exception) {
                Log.e("failed to update child class", e.message.toString())
            }
            getAllChildClasses()
        }

    }

    fun deleteChildClass(
        selectedChildClass: ChildClassResponse
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.deleteChildClass(
                    childClass = selectedChildClass
                )
            } catch (e: Exception) {
                Log.e("failed to delete child class", e.message.toString())
            }

            getAllChildClasses()
        }

    }
}