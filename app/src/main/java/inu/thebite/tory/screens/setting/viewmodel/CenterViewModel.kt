package inu.thebite.tory.screens.setting.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.model.center.CenterRequest
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.repositories.Center.CenterRepo
import inu.thebite.tory.repositories.Center.CenterRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import java.lang.Exception

class CenterViewModel : ViewModel() {
    private val repo: CenterRepoImpl = CenterRepoImpl()


    private val _allCenters: MutableStateFlow<List<CenterResponse>?> = MutableStateFlow(null)
    val allCenters = _allCenters.asStateFlow()

    private val _centers: MutableStateFlow<List<CenterResponse>?> = MutableStateFlow(null)
    val centers = _centers.asStateFlow()

    private val _selectedCenter = MutableStateFlow<CenterResponse?>(null)
    val selectedCenter = _selectedCenter.asStateFlow()


    fun setSelectedCenter(centerEntity: CenterResponse) {

        _selectedCenter.value = centerEntity
    }

    fun clearSelectedCenter() {
        _selectedCenter.value = null
    }
//    init {
//        getAllCenters()
//    }

    fun getAllCenters(){
        viewModelScope.launch {
            try {
                val allCenters = repo.getAllCenters()
                Log.e("가지고 온 센터", allCenters.toString())
                _allCenters.value = allCenters
            } catch (e: Exception) {
                Log.e("forEach문", e.message.toString())
            }
        }

//        viewModelScope.launch(Dispatchers.IO) {
//            repo.getAllCenters().collect{data ->
//                _allCenters.update { data }
//            }
//        }
    }


    fun createCenter(
        centerName: String,
    ) {
        viewModelScope.launch {
            try {
                val newCenterRequest = CenterRequest(
                    name = centerName
                )
                repo.createCenter(newCenterRequest)
            } catch(e : Exception) {
                Log.e("addCenter", e.message.toString())
            }
            getAllCenters()
        }
//        viewModelScope.launch(Dispatchers.IO) {
//            val newCenterEntity = CenterEntity(
//                centerName = centerName,
//            )
//            repo.createCenter(newCenterEntity)
//        }
    }

    fun updateCenter(centerEntity: CenterResponse, centerName: String) {
        viewModelScope.launch {
            try {
                val updateCenterRequest = CenterRequest(
                    name = centerName
                )
                repo.updateCenter(centerEntity, updateCenterRequest)
            } catch (e: Exception) {
                Log.e("updateCenter", e.message.toString())
            }
        }

//        viewModelScope.launch(Dispatchers.IO) {
//            repo.updateCenter(updatedCenterEntity)
//        }
    }

    fun deleteCenter(centerEntity: CenterResponse) {
        viewModelScope.launch {
            try {
                repo.deleteCenter(centerEntity)
            } catch (e: Exception) {
                Log.e("deleteCenter", e.message.toString())
            }
            getAllCenters()
        }
//        viewModelScope.launch(Dispatchers.IO) {
//            repo.deleteCenter(centerEntity)
//        }
    }
}