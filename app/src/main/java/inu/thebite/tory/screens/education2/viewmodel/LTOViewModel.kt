package inu.thebite.tory.screens.education2.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoGraphResponse
import inu.thebite.tory.model.lto.LtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.lto.UpdateLtoStatusRequest
import inu.thebite.tory.repositories.LTO.LTORepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
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

    private val _ltoGraphList: MutableStateFlow<List<LtoGraphResponse>?> = MutableStateFlow(null)
    val ltoGraphList = _ltoGraphList.asStateFlow()

    fun setSelectedLTO(ltoEntity: LtoResponse) {
        _selectedLTO.update {
            ltoEntity
        }
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

//            getLTOsByDomain()
//            getLTOsByDEV(selectedLTO.domain)
        }
    }

    fun clearSelectedCenter() {
        _selectedLTO.value = null
    }

    fun clearLTOGraphList(){
        _ltoGraphList.value = null
    }
    init {
//        getAllLTOs()
//        setLTODummyData()
        observeAllLTOs()
    }

    private fun observeAllLTOs() {
        viewModelScope.launch {
            allLTOs.onEach { allLTOs ->
                updateLTOsAndSelectedLTO(allLTOs)
            }.collect()
        }
    }

    private fun updateLTOsAndSelectedLTO(allLTOs: List<LtoResponse>?) {
        allLTOs?.let {allLTOs ->
            _ltos.update {
                allLTOs.filter { lto ->
                    ltos.value?.any { it.id == lto.id } == true
                }
            }
            _selectedLTO.update {
                allLTOs.find { lto ->
                    selectedLTO.value?.id == lto.id
                }
            }
        }
    }
//
//    fun setLTODummyData(){
//        _allLTOs.update {
//            val filteredLTOs = mutableListOf<LtoResponse>()
//            for(i in 1..10){
//                for (j in 1..10){
//                    filteredLTOs.add(
//                        LtoResponse(
//                            id = j.toLong(),
//                            templateNum = j,
//                            status = "진행중",
//                            name = "$j. 예시 데이터 LTO",
//                            contents = j.toString(),
//                            game = "",
//                            achieveDate = "",
//                            registerDate = "",
//                            delYN = "",
//                            domain = DomainResponse(
//                                id = i.toLong(),
//                                templateNum = i,
//                                type = "",
//                                status = "",
//                                name = "$i. 예시 데이터 DEV",
//                                contents = "",
//                                useYN = "",
//                                delYN = "",
//                                registerDate = ""
//                            )
//                        )
//                    )
//                }
//            }
//            filteredLTOs
//        }
//        _selectedLTO.update {
//            allLTOs.value!!.first()
//        }
//    }
    fun getLTOsByDomain(
        domainId : Long
    ){
        viewModelScope.launch {
            try {
                Log.d("getLtosByStudent", repo.getLTOsByStudent(domainId = domainId).toString())
                _allLTOs.update {
                    repo.getLTOsByStudent(domainId = domainId)
                }
            } catch (e: Exception) {
                Log.e("failed to get all LTOs", e.message.toString())
            }
        }
    }

    fun getLTOsByDEV(
        selectedDEV: DomainResponse,
    ){
        Log.d("AllLto", allLTOs.value.toString())
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

    fun addLTO(
        selectedDEV : DomainResponse,
        newLTO : LtoRequest
    ){
        viewModelScope.launch {
            try {
                val response = repo.createLTO(selectedDEV = selectedDEV, newLTO = newLTO)

                if (response.isSuccessful) {
                    val newLTOResponse = response.body() ?: throw Exception("LTO 정보가 비어있습니다.")
                    _allLTOs.update { currentLTOs ->
                        currentLTOs?.let {
                            // 현재 LTO 리스트가 null이 아니면 새 STO를 추가
                            it + newLTOResponse
                        } ?: listOf(newLTOResponse) // 현재 LTO 리스트가 null이면 새 리스트를 생성
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("LTO 추가 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to add STO", e.message.toString())
            }
        }
    }

    fun updateLTO(
        selectedLTO : LtoResponse,
        updateLTO : LtoRequest
    ){
        viewModelScope.launch {
            try {
                val response = repo.updateLto(selectedLTO = selectedLTO, ltoRequest = updateLTO)

                if (response.isSuccessful) {
                    val updatedSTO = response.body() ?: throw Exception("LTO 정보가 비어있습니다.")
                    _allLTOs.update {
                        allLTOs.value?.let { allLTOs ->
                            allLTOs.map { if (it.id == updatedSTO.id) updatedSTO else it}
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("LTO 업데이트 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to update STO", e.message.toString())
            }
        }
    }

//    fun updateSelectedLTO(
//        selectedLTOId : Long
//    ){
//        viewModelScope.launch {
//            val foundLTO = allLTOs.value!!.find {
//                it.id == selectedLTOId
//            }
//            foundLTO?.let {foundLTO ->
//                setSelectedLTO(
//                    foundLTO
//                )
//            }
//        }
//    }

    fun getLTOGraph(
        selectedLTO: LtoResponse
    ){
        viewModelScope.launch {
            try {
                val allLTOGraphList = repo.getLTOGraph(selectedLTO)
                _ltoGraphList.value = allLTOGraphList
            } catch (e: Exception) {
                Log.e("failed to get LTO Graph List", e.message.toString())
            }
        }
    }


    fun deleteLTO(
        selectedLTO : LtoResponse
    ){
        viewModelScope.launch {
            try {
                val response = repo.deleteLTO(selectedLTO = selectedLTO)

                if (response.isSuccessful) {
                    val isDeleted = response.body() ?: throw Exception("LTO 정보가 비어있습니다.")
                    if(isDeleted){
                        _allLTOs.update {currentLTOs ->
                            currentLTOs?.filterNot { it.id == selectedLTO.id }
                        }
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("LTO 식제 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to delete LTO", e.message.toString())
            }
        }
    }
}