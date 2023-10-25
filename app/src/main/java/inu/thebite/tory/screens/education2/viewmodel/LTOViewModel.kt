package inu.thebite.tory.screens.education2.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.lto.UpdateLtoStatusRequest
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

    fun setSelectedLTOStatus(selectedLTO: LtoResponse, changeState : String) {
        viewModelScope.launch {
            val updateLTOStatus = UpdateLtoStatusRequest(
                status = changeState
            )
            if(changeState == "완료"){
                repo.updateLtoHitStatus(selectedLTO, updateLTOStatus)
            } else {
                repo.updateLTOStatus(selectedLTO, updateLTOStatus)
            }

            getAllLTOs()
            getLTOsByDEV(selectedLTO.domain)
        }
    }

    fun clearSelectedCenter() {
        _selectedLTO.value = null
    }
    init {
        getAllLTOs()
//        setLTODummyData()
    }

    fun setLTODummyData(){
        _ltos.update {
            val filteredChildClasses = mutableListOf<LtoResponse>()
            for(i in 1..10){
                filteredChildClasses.add(
                    LtoResponse(
                        id = i.toLong(),
                        templateNum = i,
                        status = "",
                        name = "$i. 예시 데이터 LTO",
                        contents = i.toString(),
                        game = "",
                        achieveDate = "",
                        registerDate = "",
                        delYN = "",
                        domain = DomainResponse(
                            id = i.toLong(),
                            templateNum = i,
                            type = "",
                            status = "",
                            name = i.toString(),
                            contents = "",
                            useYN = "",
                            delYN = "",
                            registerDate = ""
                        )
                    )
                )
            }
            filteredChildClasses
        }
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
                val filteredLTOs = allLTOs.value!!.filter {
                    it.domain.id == selectedDEV.id
                }
                Log.d("allLTOs", allLTOs.toString())
                filteredLTOs
            }
        }else{
            _ltos.update { null }
        }
    }

    fun createLTO(
        selectedDEV : DomainResponse,
        newLTO : LtoRequest
    ){
        viewModelScope.launch {
            try {
                Log.d("selectedDEV", selectedDEV.toString())
                Log.d("newLTO", newLTO.toString())
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

    fun updateLTO(
        selectedLTO : LtoResponse,
        updateLTO : LtoRequest
    ){
        viewModelScope.launch {
            try {
                repo.updateLto(
                    selectedLTO = selectedLTO,
                    ltoRequest = updateLTO
                )
            } catch (e: Exception) {
                Log.e("failed to update LTO", e.message.toString())
            }
            getAllLTOs()
            getLTOsByDEV(selectedLTO.domain)
        }
    }

    fun updateSelectedLTO(
        selectedLTOId : Long
    ){
        viewModelScope.launch {
            val foundLTO = allLTOs.value!!.find {
                it.id == selectedLTOId
            }
            foundLTO?.let {foundLTO ->
                setSelectedLTO(
                    foundLTO
                )
            }
        }
    }

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