package inu.thebite.tory.screens.education2.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.AddLtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.repositories.DEV.DEVRepoImpl
import inu.thebite.tory.repositories.LTO.LTORepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LTOViewModel: ViewModel() {
    private val repo: LTORepoImpl = LTORepoImpl()


    private val _allLTOs: MutableStateFlow<List<LtoResponse>?> = MutableStateFlow(null)
    val allLTOs = _allLTOs.asStateFlow()

    private val _ltos: MutableStateFlow<List<LtoResponse>?> = MutableStateFlow(null)
    val ltos = _ltos.asStateFlow()

    private val _selectedLTO = MutableStateFlow<LtoResponse?>(null)
    val selectedLTO = _selectedLTO.asStateFlow()


    fun setSelectedLTO(ltoEntity: LtoResponse) {

        _selectedLTO.value = ltoEntity
    }

    fun clearSelectedCenter() {
        _selectedLTO.value = null
    }
    init {
        getAllLTOs()
    }

    fun getAllLTOs(){
        viewModelScope.launch {
            try {
                val allLTOs = repo.getAllLTOs()
                _allLTOs.value = allLTOs
            } catch (e: Exception) {
                Log.e("failed to get all LTOs", e.message.toString())
            }
        }
    }

    fun getLTOsByDEV(
        selectedDEV: DomainResponse,
    ){
        if(selectedDEV.isNotNull()){
            _ltos.update {
                val filteredChildClasses = allLTOs.value!!.filter {
                    it.id == selectedDEV.id
                }
                filteredChildClasses
            }
        }else{
            _ltos.update { null }
        }
    }

    fun createLTO(
        selectedDEV : DomainResponse,
        newLTO : AddLtoRequest
    ){
        viewModelScope.launch {
            try {
                repo.createLTO(
                    selectedDEV = selectedDEV,
                    newLTO = newLTO
                )
            } catch (e: Exception) {
                Log.e("failed to create LTO", e.message.toString())
            }
            getAllLTOs()
        }
    }

//    fun updateLTO(
//        selectedLTO : LtoResponse
//    ){
//        viewModelScope.launch {
//            try {
//                repo.updateLTOStatus(
//                    selectedLTO = selectedLTO,
//                )
//            } catch (e: Exception) {
//                Log.e("failed to delete LTO", e.message.toString())
//            }
//            getAllLTOs()
//        }
//    }

    fun deleteLTO(
        selectedLTO : LtoResponse
    ){
        viewModelScope.launch {
            try {
                repo.deleteLTO(
                    selectedLTO = selectedLTO,
                )
            } catch (e: Exception) {
                Log.e("failed to delete LTO", e.message.toString())
            }
            getAllLTOs()
        }
    }
}