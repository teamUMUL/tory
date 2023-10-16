package inu.thebite.tory.screens.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import inu.thebite.tory.CenterSelectViewModel
import inu.thebite.tory.ChildClassSelectViewModel
import inu.thebite.tory.database.ChildClass.ChildClassEntity
import inu.thebite.tory.repositories.ChildClass.ChildClassRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChildClassViewModel : ViewModel(), KoinComponent {
    private val repo: ChildClassRepo by inject()

    private val _allChildClasses : MutableStateFlow<List<ChildClassEntity>> = MutableStateFlow(emptyList())
    val allChildClasses = _allChildClasses.asStateFlow()

    private val _childClasses: MutableStateFlow<List<ChildClassEntity>?> = MutableStateFlow(null)
    val childClasses = _childClasses.asStateFlow()

    private val _selectedChildClass = MutableStateFlow<ChildClassEntity?>(null)
    val selectedChildClass = _selectedChildClass.asStateFlow()

    fun setSelectedChildClass(childClassEntity: ChildClassEntity) {

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

    private fun getAllChildClasses(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllChildClasses().collect{data ->
                _allChildClasses.update { data }
            }
        }
    }

    fun getChildClassesByCenterName(
        selectedCenterName: String,
    ){
        if(selectedCenterName.isEmpty()){
            _childClasses.update { null }
        }else{
            _childClasses.update {
                val filteredChildClasses = allChildClasses.value.filter {
                    it.centerName == selectedCenterName
                }
                filteredChildClasses
            }
        }
    }

    fun createChildClass(
        centerName: String,
        childClassName: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newChildClassEntity = ChildClassEntity(
                centerName = centerName,
                className = childClassName
            )
            repo.createChildClass(newChildClassEntity)

        }

    }

    fun updateChildClass(updatedChildClassEntity: ChildClassEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateChildClass(updatedChildClassEntity)

        }

    }

    fun deleteChildClass(childClassEntity: ChildClassEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteChildClass(childClassEntity)

        }

    }

    fun deleteChildClassesByCenterName(centerName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val childClassesToDelete = allChildClasses.value.filter {
                it.centerName == centerName
            }

            childClassesToDelete.forEach { childClassEntity ->
                repo.deleteChildClass(childClassEntity)
            }

        }
    }
}