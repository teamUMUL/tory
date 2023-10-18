package inu.thebite.tory.screens.education

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.repositories.Center.CenterRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class DEVViewModel: ViewModel() {

//    private val repo: CenterRepoImpl = CenterRepoImpl()


    private val _allDEVs: MutableStateFlow<List<DomainResponse>?> = MutableStateFlow(null)
    val allDEVs = _allDEVs.asStateFlow()

    private val _devs: MutableStateFlow<List<DomainResponse>?> = MutableStateFlow(null)
    val devs = _devs.asStateFlow()

    private val _selectedDEV = MutableStateFlow<DomainResponse?>(null)
    val selectedDEV = _selectedDEV.asStateFlow()


    fun setSelectedCenter(devEntity: DomainResponse) {

        _selectedDEV.value = devEntity
    }

    fun clearSelectedCenter() {
        _selectedDEV.value = null
    }
    init {
//        getAllDEVs()
    }

//    fun getAllDEVs(){
//        viewModelScope.launch {
//            try {
//                val allCenters = repo.getAllCenters()
//                _allCenters.value = allCenters
//            } catch (e: Exception) {
//                Log.e("forEach문", e.message.toString())
//            }
//        }
//
////        viewModelScope.launch(Dispatchers.IO) {
////            repo.getAllCenters().collect{data ->
////                _allCenters.update { data }
////            }
////        }
//    }

}