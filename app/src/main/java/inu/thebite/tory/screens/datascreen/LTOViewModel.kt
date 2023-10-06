package inu.thebite.tory.screens.datascreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.database.LTO.LTOEntity
import inu.thebite.tory.repositories.LTO.LTORepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@SuppressLint("MutableCollectionMutableState")
class LTOViewModel: ViewModel(), KoinComponent {


    private val repo: LTORepo by inject()

    private val _allLTOs : MutableStateFlow<List<LTOEntity>> = MutableStateFlow(emptyList())
    val allLTOs = _allLTOs.asStateFlow()

    private val _ltos: MutableStateFlow<List<LTOEntity>?> = MutableStateFlow(null)
    val ltos = _ltos.asStateFlow()

    private val _selectedLTO = MutableStateFlow<LTOEntity?>(null)
    val selectedLTO = _selectedLTO.asStateFlow()

    // Function to set the selected STO
    fun setSelectedLTO(ltoEntity: LTOEntity) {

        _selectedLTO.value = ltoEntity
    }

    fun clearSelectedLTO() {
        _selectedLTO.value = null
    }
    init {
        getAllLTOs()
    }

    private fun getAllLTOs(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllLTOs().collect{data ->
                _allLTOs.update { data }
            }
        }
    }


    fun createLTO(
        className: String,
        childName: String,
        selectedDEV: String,
        ltoName: String,
        ltoState: Int,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newLTOEntity = LTOEntity(
                className = className,
                childName = childName,
                selectedDEV = selectedDEV,
                ltoName = ltoName,
                ltoState = ltoState
            )
            repo.createLTO(newLTOEntity)
        }
    }

    fun getLTOsByCriteria(
        className: String,
        childName: String,
        selectedDEV: String,
    ){
        if(className.isEmpty() || childName.isEmpty() || selectedDEV.isEmpty()){
            _ltos.update { null }
        }else{
            _ltos.update {
                Log.d("FilteringResult", "Filtered Criteria: $className, $childName, $selectedDEV")
                val filteredLTOs = allLTOs.value.filter {
                    it.className == className &&
                    it.childName == childName &&
                    it.selectedDEV == selectedDEV
                }
                Log.d("FilteringResult", "Filtered LTOs: $filteredLTOs")
                filteredLTOs
            }
        }
    }
    fun updateLTO(updatedLTOEntity: LTOEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateLTO(updatedLTOEntity)
        }

    }

    fun deleteLTO(lto: LTOEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteLTO(lto)
        }
    }
}