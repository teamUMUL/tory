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
    init {
        getAllCenters()
    }

    fun getAllCenters(){
        viewModelScope.launch {
            try {
                val allCenters = repo.getAllCenters()
                Log.e("가지고 온 센터", allCenters.toString())
                _allCenters.value = allCenters
            } catch (e: Exception) {
                Log.e("failed to get all centers", e.message.toString())
            }
        }

    }


    fun createCenter(
        newCenter: CenterRequest,
    ) {
        viewModelScope.launch {
            try {
                repo.createCenter(newCenter)
            } catch(e : Exception) {
                Log.e("failed to create center", e.message.toString())
            }
            getAllCenters()
        }

    }

    fun updateCenter(selectedCenter: CenterResponse, updateCenter: CenterRequest) {
        viewModelScope.launch {
            try {
                repo.updateCenter(selectedCenter, updateCenter)
            } catch (e: Exception) {
                Log.e("failed to update center", e.message.toString())
            }
        }
        getAllCenters()
    }

    fun deleteCenter(centerEntity: CenterResponse) {
        viewModelScope.launch {
            try {
                repo.deleteCenter(centerEntity)
            } catch (e: Exception) {
                Log.e("failed to delete center", e.message.toString())
            }
            getAllCenters()
        }

    }
}