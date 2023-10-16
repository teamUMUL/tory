package inu.thebite.tory.screens.setting.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.CenterSelectViewModel
import inu.thebite.tory.database.Center.CenterEntity
import inu.thebite.tory.model.Center
import inu.thebite.tory.repositories.Center.CenterRepo
import inu.thebite.tory.retrofit.RetrofitApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class CenterViewModel : ViewModel(), KoinComponent {
    private val repo: CenterRepo by inject()


    private val _allCenters : MutableStateFlow<List<CenterEntity>> = MutableStateFlow(emptyList())
    val allCenters = _allCenters.asStateFlow()

    private val _centers: MutableStateFlow<List<CenterEntity>?> = MutableStateFlow(null)
    val centers = _centers.asStateFlow()

    private val _selectedCenter = MutableStateFlow<CenterEntity?>(null)
    val selectedCenter = _selectedCenter.asStateFlow()

    private val service = RetrofitApi.apiService

    fun setSelectedCenter(centerEntity: CenterEntity) {

        _selectedCenter.value = centerEntity
    }

    fun clearSelectedCenter() {
        _selectedCenter.value = null
    }
    init {
        getAllCenters()
    }

    private fun getAllCenters(){
        viewModelScope.launch {
            try {
                val returnList = mutableListOf<CenterEntity>()
                val response = service.getCenterList()
                response.forEach {
                    returnList.add(
                        CenterEntity(
                            centerName = it.name
                        )
                    )
                }
                _allCenters.update { returnList }
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
                val center = Center(centerName)
                service.addCenter(center)
            } catch(e : Exception) {
                Log.e("addCenter", e.message.toString())
            }
        }
//        viewModelScope.launch(Dispatchers.IO) {
//            val newCenterEntity = CenterEntity(
//                centerName = centerName,
//            )
//            repo.createCenter(newCenterEntity)
//        }
    }

    fun updateCenter(updatedCenterEntity: CenterEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateCenter(updatedCenterEntity)
        }


    }

    fun deleteCenter(centerEntity: CenterEntity) {
        viewModelScope.launch {
            try {
                service.deleteCenter(centerEntity.centerName)
            } catch (e: Exception) {
                Log.e("deleteCenter", e.message.toString())
            }
        }
//        viewModelScope.launch(Dispatchers.IO) {
//            repo.deleteCenter(centerEntity)
//        }
    }
}