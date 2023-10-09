package inu.thebite.tory.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.database.Center.CenterEntity
import inu.thebite.tory.database.ChildClass.ChildClassEntity
import inu.thebite.tory.repositories.Center.CenterRepo
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
}