package inu.thebite.tory.screens.education.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.repositories.DEV.DEVRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    init {
        getAllDEVs()
//        setDummyDEVData()
    }

    fun setDummyDEVData(){
        val dummyDataList = mutableListOf<DomainResponse>()
        for(i in 1..10){
            val dummyData = DomainResponse(
                id = i.toLong(),
                templateNum = i,
                type = "",
                status = "",
                name = "$i. 예시 데이터 DEV",
                contents = "",
                useYN = "",
                delYN = "",
                registerDate = ""
            )
            dummyDataList.add(dummyData)
        }
        _allDEVs.value = dummyDataList
        _selectedDEV.update {
            allDEVs.value!!.first()
        }
    }


    fun getAllDEVs(){
        viewModelScope.launch {
            try {
                _allDEVs.update {
                    repo.getAllDEVs()
                }
            } catch (e: Exception) {
                Log.e("failed to get all DEVs", e.message.toString())
            }
        }
    }

    fun findDEVById(devId : Long): DomainResponse? {
        return allDEVs.value!!.find {
            it.id == devId
        }
    }


}