package inu.thebite.tory.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.database.Center.CenterEntity
import inu.thebite.tory.repositories.Center.CenterRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CenterViewModel : ViewModel(), KoinComponent {
    private val repo: CenterRepo by inject()

    private val _allCenters : MutableStateFlow<List<CenterEntity>> = MutableStateFlow(emptyList())
    val allCenters = _allCenters.asStateFlow()

    private val _centers: MutableStateFlow<List<CenterEntity>?> = MutableStateFlow(null)
    val centers = _centers.asStateFlow()

    private val _selectedCenter = MutableStateFlow<CenterEntity?>(null)
    val selectedCenter = _selectedCenter.asStateFlow()

    fun setSelectedCenter(centerEntity: CenterEntity) {

        _selectedCenter.value = centerEntity
    }

    fun clearSelectedCenter() {
        _selectedCenter.value = null
    }
    init {
        getAllCenters()
    }

    private fun getAllCenters(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllCenters().collect{data ->
                _allCenters.update { data }
            }
        }
    }


    fun createCenter(
        centerName: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newCenterEntity = CenterEntity(
                centerName = centerName,
            )
            repo.createCenter(newCenterEntity)
        }
    }

    fun updateCenter(updatedCenterEntity: CenterEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateCenter(updatedCenterEntity)
        }

    }

    fun deleteCenter(centerEntity: CenterEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteCenter(centerEntity)
        }
    }
}