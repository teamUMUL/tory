package inu.thebite.tory.screens.education2.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.lto.UpdateLtoStatusRequest
import inu.thebite.tory.model.sto.AddStoRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.UpdateStoStatusRequest
import inu.thebite.tory.repositories.STO.STORepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class STOViewModel : ViewModel() {
    private val repo: STORepoImpl = STORepoImpl()


    private val _allSTOs: MutableStateFlow<List<StoResponse>?> = MutableStateFlow(null)
    val allSTOs = _allSTOs.asStateFlow()

    private val _stos: MutableStateFlow<List<StoResponse>?> = MutableStateFlow(null)
    val stos = _stos.asStateFlow()

    private val _selectedSTO = MutableStateFlow<StoResponse?>(null)
    val selectedSTO = _selectedSTO.asStateFlow()


    fun setSelectedSTO(stoEntity: StoResponse) {

        _selectedSTO.value = stoEntity
    }


    fun clearSelectedSTO() {
        _selectedSTO.value = null
    }
    init {
        getAllSTOs()
    }

    fun getAllSTOs(){
        viewModelScope.launch {
            try {
                val allSTOs = repo.getStoList()
                _allSTOs.value = allSTOs
            } catch (e: Exception) {
                Log.e("failed to get all STOs", e.message.toString())
            }
        }
    }
    fun setSelectedSTOStatus(selectedSTO: StoResponse, changeState : String) {
        viewModelScope.launch {
            val updateSTOStatus = UpdateStoStatusRequest(
                status = changeState
            )
            if(changeState == "준거 도달"){
                repo.updateStoHitStatus(selectedSTO, updateSTOStatus)
            } else {
                repo.updateStoStatus(selectedSTO, updateSTOStatus)
            }
            getAllSTOs()
            getSTOsByLTO(selectedSTO.lto)
        }
    }
    fun getSTOsByLTO(
        selectedLTO: LtoResponse,
    ){
        if(selectedLTO.isNotNull()){
            _stos.update {
                val filteredChildClasses = allSTOs.value!!.filter {
                    it.lto.id == selectedLTO.id
                }
                Log.d("allSTOs", allSTOs.toString())
                filteredChildClasses
            }
        }else{
            _stos.update { null }
        }
    }

    fun createSTO(
        selectedLTO: LtoResponse,
        newSTO : AddStoRequest
    ){
        viewModelScope.launch {
            try {
                repo.addSto(
                    ltoInfo = selectedLTO,
                    addStoRequest = newSTO
                )
            } catch (e: Exception) {
                Log.e("failed to create STO", e.message.toString())
            }
            getAllSTOs()
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

    fun deleteSTO(
        selectedSTO : StoResponse
    ){
        viewModelScope.launch {
            try {
                repo.deleteSto(
                    stoInfo = selectedSTO,
                )
            } catch (e: Exception) {
                Log.e("failed to delete STO", e.message.toString())
            }
            getAllSTOs()
        }
    }
}
