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
import kotlinx.coroutines.Dispatchers
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

//    fun setSelectedLTOStatus(selectedLTO: LtoResponse, changeState : String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val foundLTO = _ltos.value!!.find {
//                it.id == selectedLTO.id
//            }
//            foundLTO?.status = changeState
//            _selectedLTO.value = foundLTO
//        }
//    }

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
                val filteredChildClasses = allLTOs.value!!.filter {
                    it.domain.id == selectedDEV.id
                }
                Log.d("allLTOs", allLTOs.toString())
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