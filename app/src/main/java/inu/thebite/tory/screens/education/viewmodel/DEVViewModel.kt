package inu.thebite.tory.screens.education.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.model.domain.AddDomainRequest
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.repositories.DEV.DEVRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DEVViewModel : ViewModel() {
    private val repo: DEVRepoImpl = DEVRepoImpl()


    private val _allDEVs: MutableStateFlow<List<DomainResponse>?> = MutableStateFlow(null)
    val allDEVs = _allDEVs.asStateFlow()

    private val _devs: MutableStateFlow<List<DomainResponse>?> = MutableStateFlow(null)
    val devs = _devs.asStateFlow()

    private val _selectedDEV = MutableStateFlow<DomainResponse?>(null)
    val selectedDEV = _selectedDEV.asStateFlow()


    fun setSelectedDEV(devEntity: DomainResponse) {
        _selectedDEV.update {
            devEntity
        }
    }

    fun clearSelectedCenter() {
        _selectedDEV.value = null
    }

    fun clearAll(){
        _allDEVs.update { null }
        _devs.update { null }
        _selectedDEV.update { null }
    }
    init {
//        getAllDEVs()
//        setDummyDEVData()
        observeAllDEVs()
    }

    private fun observeAllDEVs() {
        viewModelScope.launch {
            allDEVs.onEach { allDEVs ->
                updateSelectedDEV(allDEVs)
            }.collect()
        }
    }

    private fun updateSelectedDEV(allDEVs: List<DomainResponse>?) {
        allDEVs?.let {allDEVs ->
            if (selectedDEV.value == null){
                _selectedDEV.update {
                    allDEVs.firstOrNull()
                }
            }
        }
    }

    fun setDummyDEVData(){
        val dummyDataList = mutableListOf<DomainResponse>()
        for(i in 1..10){
            val dummyData = DomainResponse(
                id = i.toLong(),
                templateNum = i,
                name = "$i. 예시 데이터 DEV",
                registerDate = "",
                centerId = 1L
            )
            dummyDataList.add(dummyData)
        }
        _allDEVs.value = dummyDataList
        _selectedDEV.update {
            allDEVs.value!!.first()
        }
    }

    fun addDEV(
        addDomainRequest: AddDomainRequest,
        centerId: Long
    ){
        viewModelScope.launch {
            try {
                val response = repo.createDEV(newDEV = addDomainRequest, centerId = centerId)

                if (response.isSuccessful) {
                    val newDEVResponse = response.body() ?: throw Exception("DEV 정보가 비어있습니다.")
                    _allDEVs.update { currentDEVs ->
                        currentDEVs?.let {
                            // 현재 DEV 리스트가 null이 아니면 새 STO를 추가
                            it + newDEVResponse
                        } ?: listOf(newDEVResponse) // 현재 DEV 리스트가 null이면 새 리스트를 생성
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("DEV 추가 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to add DEV", e.message.toString())
            }
        }
    }


    fun getAllDEVs(centerId: Long){
        viewModelScope.launch {
            try {
                _allDEVs.update {
                    repo.getAllDEVs(centerId = centerId)
                }
            } catch (e: Exception) {
                Log.e("failed to get all DEVs", e.message.toString())
            }
        }
    }

    fun findDEVById(devId : Long): DomainResponse? {
        val allDEVs = allDEVs.value ?: emptyList()
        return allDEVs.find {
            it.id == devId
        }
    }


}