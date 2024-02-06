package inu.thebite.tory.screens.education.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import es.dmoral.toasty.Toasty
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

    fun clearAll(){
        _allLTOs.update { null }
        _ltos.update { null }
        _selectedLTO.update { null }
        _ltoGraphList.update { null }
    }

    fun setSelectedLTO(ltoEntity: LtoResponse) {
        _selectedLTO.update {
            ltoEntity
        }
    }

    fun setSelectedLTOStatus(selectedLTO: LtoResponse, changeState : String) {
        viewModelScope.launch {
            try {
                val updateLtoStatusRequest = UpdateLtoStatusRequest(
                    status = changeState
                )
                val response = if (changeState == "준거 도달") {
                    repo.updateLtoHitStatus(selectedLTO = selectedLTO, updateLtoStatusRequest = updateLtoStatusRequest)
                } else {
                    repo.updateLTOStatus(selectedLTO = selectedLTO, updateLtoStatusRequest = updateLtoStatusRequest)
                }

                if (response.isSuccessful) {
                    val updatedLTO = response.body() ?: throw Exception("LTO 정보가 비어있습니다.")
                    Log.d("updatedLTO", updatedLTO.toString())
                    _allLTOs.update { currentLTOs ->
                        currentLTOs?.map { lto ->
                            if(lto.id == selectedLTO.id) updatedLTO else lto
                        }
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("LTO 상태 업데이트 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to update LTO Status", e.message.toString())
            }
        }
    }

    fun clearSelectedLTO() {
        _selectedLTO.value = null
    }

    fun clearLTOGraphList(){
        _ltoGraphList.value = null
    }
    init {
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
            _ltos.update {currentLTOs ->
                Log.d("allLTOs", allLTOs.toString())
                allLTOs.filter {lto ->
                    currentLTOs?.map { it.id }?.contains(lto.id) == true
                }
            }
            _selectedLTO.update {
                allLTOs.find { lto ->
                    selectedLTO.value?.id == lto.id
                }
            }
        }
    }

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
//                            ),
//                            student = StudentResponse(
//                                id = 1L,
//                                name = "",
//                                birth ="",
//                                etc = "",
//                                parentName = "",
//                                startDate = "",
//                                endDate = "",
//                                registerDate = "",
//                                childClass = ChildClassResponse(
//                                    id = 1L,
//                                    name = "",
//                                    center = CenterResponse(
//                                        id = 1L,
//                                        name = ""
//                                    )
//                                )
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
    fun findLTOById(
        ltoId: Long
    ): LtoResponse? {
        return allLTOs.value!!.find {
            it.id == ltoId
        }
    }

    fun findLTOsByIds(
        ltoIds: List<Long>
    ): List<LtoResponse> {
        return allLTOs.value!!.filter {
            ltoIds.contains(it.id)
        }
    }

    fun getAllLTOs(studentId: Long){
        viewModelScope.launch{
            try {
                _allLTOs.update {
                    val allLTOs = repo.getAllLTOs(studentId = studentId)
                    Log.d("allLTOsByStudent", allLTOs.toString())
                    allLTOs
                }

            } catch (e: Exception){
                Log.e("failed to get all LTOs", e.message.toString())
            }
            Log.d("allLTOs", allLTOs.value.toString())
        }
    }
//
//    fun getLTOsByDomain(
//        domainId : Long
//    ){
//        viewModelScope.launch {
//            try {
//                _ltos.update {
//                    allLTOs.value!!.filter {
//                        it.domainId == domainId
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("failed to get LTOs by DomainId", e.message.toString())
//            }
//        }
//    }
    fun getLTOsByDomain(
        studentId: Long,
        domainId : Long
    ){
        viewModelScope.launch {
            try {
                Log.d("getLtosByStudent", repo.getLTOsByStudent(studentId = studentId,domainId = domainId).toString())
                _ltos.update {
                    repo.getLTOsByStudent(studentId = studentId,domainId = domainId)
                }
            } catch (e: Exception) {
                Log.e("failed to get all LTOs", e.message.toString())
            }
        }
    }

    fun addLTO(
        selectedDEV : DomainResponse,
        newLTO : LtoRequest,
        studentId: Long
    ){
        viewModelScope.launch {
            try {
                val response = repo.addLTO(selectedDEV = selectedDEV, newLTO = newLTO, studentId = studentId)
                Log.d("response", response.toString())
                if (response.isSuccessful) {
                    val newLTOResponse = response.body() ?: throw Exception("LTO 정보가 비어있습니다.")
                    _ltos.update {
                        _ltos.value?.let {currentLTOs ->
                            // 현재 LTO 리스트가 null이 아니면 새 STO를 추가
                            currentLTOs + newLTOResponse
                        } ?: listOf(newLTOResponse) // 현재 LTO 리스트가 null이면 새 리스트를 생성
                    }
                    _allLTOs.update {
                        _allLTOs.value?.let {currentLTOs ->
                            // 현재 LTO 리스트가 null이 아니면 새 STO를 추가
                            currentLTOs + newLTOResponse
                        } ?: listOf(newLTOResponse) // 현재 LTO 리스트가 null이면 새 리스트를 생성
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("LTO 추가 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to add LTO", e.message.toString())
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
                    Log.d("response", updatedSTO.toString())
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
        context: Context,
        selectedLTO: LtoResponse
    ){
        viewModelScope.launch {
            try {

                if (repo.getLTOGraph(selectedLTO).isNotNull()){
                    _ltoGraphList.update {
                        repo.getLTOGraph(selectedLTO)
                    }
                } else {
                    Toasty.warning(context, "해당 LTO에 저장된 데이터가 존재하지 않습니다", Toast.LENGTH_SHORT, true).show()
                }

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
                    Log.d("isDeleted", isDeleted.toString())
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