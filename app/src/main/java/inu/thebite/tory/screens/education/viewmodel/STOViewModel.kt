package inu.thebite.tory.screens.education.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.model.detail.DetailGraphResponse
import inu.thebite.tory.model.image.UpdateImageListRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.point.AddPointRequest
import inu.thebite.tory.model.point.DeletePointRequest
import inu.thebite.tory.model.sto.AddStoRequest
import inu.thebite.tory.model.sto.EtcRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.UpdateStoRequest
import inu.thebite.tory.model.sto.UpdateStoRoundRequest
import inu.thebite.tory.model.sto.UpdateStoStatusRequest
import inu.thebite.tory.repositories.STO.STORepoImpl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class STOViewModel : ViewModel() {
    private val repo: STORepoImpl = STORepoImpl()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("STOViewModel", "Error: ${exception.localizedMessage}")
    }

    private val _allSTOs: MutableStateFlow<List<StoResponse>?> = MutableStateFlow(null)
    val allSTOs = _allSTOs.asStateFlow()

    private val _isSTOListLoading = MutableStateFlow(true)
    val isSTOListLoading = _isSTOListLoading.asStateFlow()
    private val _todoSTOList: MutableStateFlow<List<StoResponse>?> = MutableStateFlow(null)
    val todoSTOList = _todoSTOList.asStateFlow()
//    private val _stos: MutableStateFlow<List<StoResponse>?> = MutableStateFlow(null)
//    val stos = _stos.asStateFlow()

    private val _selectedSTO = MutableStateFlow<StoResponse?>(null)
    val selectedSTO = _selectedSTO.asStateFlow()

    private val _points = MutableStateFlow<List<String>?>(null)
    val points = _points.asStateFlow()

    private val _isSavingData = MutableStateFlow(false)
    val isSavingData = _isSavingData.asStateFlow()

    private val _hasFocus = MutableStateFlow(false)
    val hasFocus = _hasFocus.asStateFlow()

    private val _userInput = MutableStateFlow("")
    val userInput = _userInput.asStateFlow()

    fun updateFocus(isFocus: Boolean){
        Log.d("isFocus", isFocus.toString())
        _hasFocus.update { isFocus }
    }

    fun setSelectedSTO(stoEntity: StoResponse) {
        _selectedSTO.update {
            stoEntity
        }
        _userInput.update {
            stoEntity.significant
        }
    }

    fun clearSelectedSTO() {
        _selectedSTO.update {
            null
        }
    }

    fun clearAll(){
        _todoSTOList.update { null }
        _allSTOs.update { null }
        _selectedSTO.update { null }
        _points.update { null }
    }

    init {
//        getDummySTO()
        observeAllSTOs()
        viewModelScope.launch {
            userInput
                .debounce(1500)
                .collect { input ->
                    selectedSTO.value?.let { selectedSTO ->
                        saveDataToServer(selectedSTO,input)
                    }
                }
        }
    }

    fun updateInput(input: String){
        _userInput.update { input }
    }

    private  fun saveDataToServer(selectedSTO: StoResponse,input: String){
        _isSavingData.update { true }

        viewModelScope.launch {
            try {
                Log.d("saveDataToServer", input)
                updateSTOSignificant(stoId = selectedSTO.id, etcRequest = EtcRequest(input))
                delay(2000)
            } finally {
                _isSavingData.update { false }
            }
        }
    }

    private fun observeAllSTOs() {
        viewModelScope.launch {

            allSTOs.onEach { allSTOs ->
                updateSTOsAndSelectedSTO(allSTOs)
            }.collect()
        }
    }

    private fun updateSTOsAndSelectedSTO(allSTOs: List<StoResponse>?) {
        allSTOs?.let { allSTOs ->
            _todoSTOList.update {currentSTOs ->
                allSTOs.filter {sto ->
                    currentSTOs?.map { it.id }?.contains(sto.id) == true
                }
            }
            _selectedSTO.update {
                val foundSTO = allSTOs.find { sto ->
                    selectedSTO.value?.id == sto.id
                }
                foundSTO
            }
        }
    }

//    fun getDummySTO() {
//        _allSTOs.update {
//            val filteredSTO = mutableListOf<StoResponse>()
//            for (i in 1..10) {
//                for (j in 1..10) {
//                    for (k in 1..10) {
//                        filteredSTO.add(
//                            StoResponse(
//                                id = k.toLong(),
//                                templateNum = k,
//                                status = "준거 도달",
//                                name = "$k 번째 예시 데이터 STO",
//                                contents = "3 Array\n목표아이템 : 공, 블럭, 색연필\n예시아이템 : 색연필",
//                                count = 20,
//                                goal = 90,
//                                goalPercent = 90,
//                                achievementOrNot = "",
//                                urgeType = "",
//                                urgeContent = "",
//                                enforceContent = "",
//                                memo = "",
//                                hitGoalDate = "",
//                                registerDate = "2023/11/07 AM 9:21",
//                                delYN = "",
//                                round = 0,
//                                imageList = emptyList(),
//                                pointList = emptyList(),
//                                lto = LtoResponse(
//                                    id = j.toLong(),
//                                    templateNum = j,
//                                    status = "진행중",
//                                    name = "$j. 예시 데이터 LTO",
//                                    contents = j.toString(),
//                                    game = "",
//                                    achieveDate = "",
//                                    registerDate = "",
//                                    delYN = "",
//                                    domain = DomainResponse(
//                                        id = i.toLong(),
//                                        templateNum = i,
//                                        type = "",
//                                        status = "",
//                                        name = "$i. 예시 데이터 DEV",
//                                        contents = "",
//                                        useYN = "",
//                                        delYN = "",
//                                        registerDate = ""
//                                    )
//                                )
//                            )
//                        )
//                    }
//                }
//            }
//            filteredSTO
//        }
//        _selectedSTO.update {
//            allSTOs.value!!.first()
//        }
//    }

    fun setSelectedSTOStatus(selectedSTO: StoResponse, changeState: String) {
        viewModelScope.launch {
            try {
                val updateSTOStatus = UpdateStoStatusRequest(
                    status = changeState
                )
                val response = if (changeState == "준거 도달") {
                    repo.updateStoHitStatus(selectedSTO, updateSTOStatus)
                } else {
                    repo.updateStoStatus(selectedSTO, updateSTOStatus)
                }
                Log.d("response", response.toString())
                if (response.isSuccessful) {
                    val updatedSTO = response.body() ?: throw Exception("STO 정보가 비어있습니다.")
                    Log.d("updatedSTO", updatedSTO.toString())
                    _allSTOs.update { currentSTOs ->
                        currentSTOs?.map { sto ->
                            if (sto.id == selectedSTO.id) updatedSTO else sto
                        }
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("STO 상태 업데이트 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to update STO Status", e.message.toString())
            }
        }
    }

    fun getAllSTOs(studentId: Long) {
        viewModelScope.launch(coroutineExceptionHandler) {
            _isSTOListLoading.update { true }
            try {
                // withTimeoutOrNull을 사용하여 5초 동안 작업을 시도합니다.
                val result = withTimeoutOrNull(5000L) {
                    repo.getAllSTOs(studentId = studentId)
                }

                if (result == null) {
                    // 시간 초과로 인해 작업이 완료되지 않았을 경우의 처리를 여기에 작성합니다.
                    Log.e("getAllSTOs", "시간 초과로 STO 목록을 가져오지 못했습니다.")
                    // 여기서 사용자에게 시간 초과를 알리는 로직을 추가할 수 있습니다.
                } else {
                    // 시간 내에 작업이 성공적으로 완료되었을 때의 처리를 여기에 작성합니다.
                    _allSTOs.update { result }
                }
            } catch (e: Exception) {
                Log.e("getAllSTOs", "STO 목록 가져오기 실패: ${e.message}", e)
                // 예외 처리 로직을 여기에 작성합니다.
            } finally {
                _isSTOListLoading.update { false }
            }
        }
    }
    fun getSTOsByIds(
        stoIds : List<Long>
    ) : List<StoResponse> {
        return allSTOs.value?.filter { sto ->
            stoIds.contains(sto.id)
        } ?: emptyList()
    }

    fun getSTOsByLTO(
        selectedLTO: LtoResponse,
    ): List<StoResponse> {
        Log.d("allSTOs", allSTOs.value.toString())

        return allSTOs.value?.filter { sto ->
            sto.ltoId == selectedLTO.id
        } ?: emptyList()
    }

    fun createSTO(
        selectedLTO: LtoResponse,
        newSTO: AddStoRequest
    ) {
        Log.d("selectedSTO Goal", newSTO.goal.toString())
        viewModelScope.launch {
            try {
                val response = repo.addSto(ltoInfo = selectedLTO, addStoRequest = newSTO)

                if (response.isSuccessful) {
                    val newStoResponse = response.body() ?: throw Exception("STO 정보가 비어있습니다.")
                    _allSTOs.update { currentSTOs ->
                        currentSTOs?.let {
                            // 현재 STO 리스트가 null이 아니면 새 STO를 추가
                            it + newStoResponse
                        } ?: listOf(newStoResponse) // 현재 STO 리스트가 null이면 새 리스트를 생성
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("STO 추가 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to add STO", e.message.toString())
            }
        }
    }

    fun getPointList(
        selectedSTO: StoResponse
    ) {
        viewModelScope.launch {
            try {
                val points = repo.getPointList(selectedSTO)
                _points.value = points
            } catch (e: Exception) {
                Log.e("failed to get Point List", e.message.toString())
            }
        }
    }

    fun clearPointList() {
        _points.value = null
    }

    fun addPoint(
        selectedSTO: StoResponse,
        addPointRequest: AddPointRequest
    ) {
        viewModelScope.launch {
            try {
                repo.addPoint(selectedSTO, addPointRequest)
            } catch (e: Exception) {
                Log.e("failed to add Point", e.message.toString())
            }
            getPointList(selectedSTO)
        }
    }

    fun deletePoint(
        selectedSTO: StoResponse,
        deletePointRequest: DeletePointRequest
    ) {
        viewModelScope.launch {
            try {
                repo.deletePoint(selectedSTO)
            } catch (e: Exception) {
                Log.e("failed to delete Point", e.message.toString())
            }
            getPointList(selectedSTO)
        }
    }

    fun addRound(
        selectedSTO: StoResponse,
        registrant: String,
        plusRate: Float,
        minusRate: Float,
        status: String,
    ) {
        viewModelScope.launch {
            try {
                val response = if (status == "준거 도달") {
                    repo.addRoundHit(
                        selectedSTO, updateStoRoundRequest = UpdateStoRoundRequest(
                            registrant = registrant,
                            plusRate = plusRate,
                            minusRate = minusRate,
                            status = status
                        )
                    )
                } else {
                    repo.addRound(
                        selectedSTO, updateStoRoundRequest = UpdateStoRoundRequest(
                            registrant = registrant,
                            plusRate = plusRate,
                            minusRate = minusRate,
                            status = status
                        )
                    )
                }



                if (response.isSuccessful) {
                    val addRoundSTO = response.body() ?: throw Exception("회차추가 STO 정보가 비어있습니다.")
                    Log.d("addRoundSTO", addRoundSTO.toString())
                    _allSTOs.update {
                        allSTOs.value?.let { allSTOs ->
                            allSTOs.map { if (it.id == addRoundSTO.id) addRoundSTO else it }
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("STO 라운드 추가 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to add Round", e.message.toString())
            }
        }
    }

    fun findSTOById(
        selectedSTOId: Long
    ): StoResponse? {
        val foundSTO = allSTOs.value!!.find {
            it.id == selectedSTOId
        }
        return foundSTO
    }

    fun findSTOsByIds(
        selectedSTOsIds: List<Long>
    ): List<StoResponse> {
        val allStos = allSTOs.value ?: return emptyList()
        Log.d("allSTOTodo", allStos.toString())
        // selectedSTOsIds에 따라 allStos를 필터링하고 정렬합니다.
        return allStos.filter { sto ->
            selectedSTOsIds.contains(sto.id)
        }.sortedBy { sto ->
            selectedSTOsIds.indexOf(sto.id)
        }
    }

    fun setTodoSTOListByIds(
        selectedSTOsIds: List<Long>
    ){
        val allStos = allSTOs.value ?: emptyList()
        Log.d("allSTOTodo", allStos.toString())
        // selectedSTOsIds에 따라 allStos를 필터링하고 정렬합니다.
        _todoSTOList.update {
            allStos.filter { sto ->
                selectedSTOsIds.contains(sto.id)
            }.sortedBy { sto ->
                selectedSTOsIds.indexOf(sto.id)
            }
        }
    }

    fun updateSTO(
        selectedSTO: StoResponse,
        updateSTO: UpdateStoRequest
    ) {
        viewModelScope.launch {
            try {
                val response = repo.updateSto(stoInfo = selectedSTO, updateStoRequest = updateSTO)

                if (response.isSuccessful) {
                    val updatedSTO = response.body() ?: throw Exception("STO 정보가 비어있습니다.")
                    _allSTOs.update {
                        allSTOs.value?.let { allSTOs ->
                            allSTOs.map { if (it.id == updatedSTO.id) updatedSTO else it }
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("STO 업데이트 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to update STO", e.message.toString())
            }
        }
    }

    fun updateSTOLooseCannon(
        stoId: Long,
        etcRequest: EtcRequest
    ) {
        viewModelScope.launch {
            try {
                val response = repo.updateLooseCannons(stoId = stoId, etcRequest = etcRequest)

                if (response.isSuccessful) {
                    val updatedSTO = response.body() ?: throw Exception("STO 정보가 비어있습니다.")
                    _allSTOs.update {
                        allSTOs.value?.let { allSTOs ->
                            allSTOs.map { if (it.id == updatedSTO.id) updatedSTO else it }
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("STO 업데이트 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to update STO", e.message.toString())
            }
        }
    }

    fun removeSTOLooseCannon(
        stoId: Long,
        etcRequest: EtcRequest
    ) {
        viewModelScope.launch {
            try {
                val response = repo.removeLooseCannon(stoId = stoId, etcRequest = etcRequest)

                if (response.isSuccessful) {
                    val updatedSTO = response.body() ?: throw Exception("STO 정보가 비어있습니다.")
                    Log.d("updatedSTOLooseCannons", updatedSTO.looseCannonList.toString())
                    _allSTOs.update {
                        allSTOs.value?.let { allSTOs ->
                            allSTOs.map { if (it.id == updatedSTO.id) updatedSTO else it }
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("STO 업데이트 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to update STO", e.message.toString())
            }
        }
    }

    fun updateSTOStressStatus(
        stoId: Long,
        etcRequest: EtcRequest
    ) {
        viewModelScope.launch {
            try {
                val response = repo.updateStressStatus(stoId = stoId, etcRequest = etcRequest)

                if (response.isSuccessful) {
                    val updatedSTO = response.body() ?: throw Exception("STO 정보가 비어있습니다.")
                    _allSTOs.update {
                        allSTOs.value?.let { allSTOs ->
                            allSTOs.map { if (it.id == updatedSTO.id) updatedSTO else it }
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("STO 업데이트 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to update STO", e.message.toString())
            }
        }
    }

    fun updateSTOConcentration(
        stoId: Long,
        etcRequest: EtcRequest
    ) {
        viewModelScope.launch {
            try {
                val response = repo.updateConcentration(stoId = stoId, etcRequest = etcRequest)

                if (response.isSuccessful) {
                    val updatedSTO = response.body() ?: throw Exception("STO 정보가 비어있습니다.")
                    _allSTOs.update {
                        allSTOs.value?.let { allSTOs ->
                            allSTOs.map { if (it.id == updatedSTO.id) updatedSTO else it }
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("STO 업데이트 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to update STO", e.message.toString())
            }
        }
    }

    fun updateSTOSignificant(
        stoId: Long,
        etcRequest: EtcRequest
    ) {
        viewModelScope.launch {
            try {
                val response = repo.updateSignificant(stoId = stoId, etcRequest = etcRequest)

                if (response.isSuccessful) {
                    val updatedSTO = response.body() ?: throw Exception("STO 정보가 비어있습니다.")
                    _allSTOs.update {
                        allSTOs.value?.let { allSTOs ->
                            allSTOs.map { if (it.id == updatedSTO.id) updatedSTO else it }
                        }
                    }
                    _userInput.update {
                        updatedSTO.significant
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("STO 업데이트 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to update STO", e.message.toString())
            }
        }
    }

    fun updateSTOImageList(
        selectedSTO: StoResponse,
        updateImageList: List<String>
    ) {
        viewModelScope.launch {
            try {
                val response = repo.updateImageList(
                    stoInfo = selectedSTO,
                    updateImageListRequest = UpdateImageListRequest(imageList = updateImageList)
                )

                if (response.isSuccessful) {
                    val updatedSTO = response.body() ?: throw Exception("STO 정보가 비어있습니다.")
                    _allSTOs.update {
                        allSTOs.value?.let { allSTOs ->
                            allSTOs.map { if (it.id == updatedSTO.id) updatedSTO else it }
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("STO 이미지 업데이트 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to update STO ImageList", e.message.toString())
            }
        }
        viewModelScope.launch {
            try {

            } catch (e: Exception) {
                Log.e("failed to update STO ImageList", e.message.toString())
            }
        }
    }


    fun deleteSTO(
        selectedSTO: StoResponse
    ) {
        viewModelScope.launch {
            try {
                val response = repo.deleteSto(stoInfo = selectedSTO)

                if (response.isSuccessful) {
                    val isDeleted = response.body() ?: throw Exception("STO 정보가 비어있습니다.")
                    if (isDeleted) {
                        _allSTOs.update { currentSTOs ->
                            currentSTOs?.filterNot { it.id == selectedSTO.id }
                        }
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("STO 식제 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to delete STO", e.message.toString())
            }
        }
    }
}
