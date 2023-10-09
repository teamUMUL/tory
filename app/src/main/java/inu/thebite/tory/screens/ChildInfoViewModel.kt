package inu.thebite.tory.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.database.ChildClass.ChildClassEntity
import inu.thebite.tory.database.ChildInfo.ChildInfoEntity
import inu.thebite.tory.repositories.ChildClass.ChildClassRepo
import inu.thebite.tory.repositories.ChildInfo.ChildInfoRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChildInfoViewModel : ViewModel(), KoinComponent {
    private val repo: ChildInfoRepo by inject()

    private val _allChildInfos : MutableStateFlow<List<ChildInfoEntity>> = MutableStateFlow(emptyList())
    val allChildInfos = _allChildInfos.asStateFlow()

    private val _childInfos: MutableStateFlow<List<ChildInfoEntity>?> = MutableStateFlow(null)
    val childInfos = _childInfos.asStateFlow()

    private val _selectedChildInfo = MutableStateFlow<ChildInfoEntity?>(null)
    val selectedChildInfo = _selectedChildInfo.asStateFlow()

    fun setSelectedChildInfo(childInfoEntity: ChildInfoEntity) {

        _selectedChildInfo.value = childInfoEntity
    }

    fun clearSelectedChildInfo() {
        _selectedChildInfo.value = null
    }
    init {
        getAllChildInfos()
    }

    private fun getAllChildInfos(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllChildInfos().collect{data ->
                _allChildInfos.update { data }
            }
        }
    }



    fun createChildInfo(
        centerName: String,
        childClassName: String,
        childName: String,
        childBirth: String,
        childEtc: String,
        parentName: String,
        startDate: String,
        endDate: String,
        registerDate: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newChildClassEntity = ChildInfoEntity(
                centerName = centerName,
                className = childClassName,
                childName = childName,
                child_birth = childBirth,
                child_etc = childEtc,
                parent_name = parentName,
                child_startDate = startDate,
                child_endDate = endDate,
                child_registerDate = registerDate
            )
            repo.createChildInfo(newChildClassEntity)
        }
    }

    fun updateChildInfo(updatedChildInfoEntity: ChildInfoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateChildInfo(updatedChildInfoEntity)
        }

    }

    fun deleteChildInfo(childInfoEntity: ChildInfoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteChildInfo(childInfoEntity)
        }
    }
}