package inu.thebite.tory.screens.teachingboard.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.repositories.Center.CenterRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

//센터를 선택할 때 사용하는 ViewModel 선택과 조회만 가능함(삭제, 수정은 불가능)
class CenterSelectViewModel : ViewModel() {
    private val repo: CenterRepoImpl = CenterRepoImpl()

    private val _allCenters : MutableStateFlow<List<CenterResponse>> = MutableStateFlow(emptyList())
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


    private val _tempSelectedCenter = MutableStateFlow<CenterResponse?>(null)
    val tempSelectedCenter = _tempSelectedCenter.asStateFlow()

    fun setTempSelectedCenter(centerEntity: CenterResponse) {

        _tempSelectedCenter.value = centerEntity
    }

    fun clearTempSelectedCenter() {
        _tempSelectedCenter.value = null
    }


    init {
//        setSelectedCenter(
//            centerEntity = CenterResponse(
//                id = 1L,
//                name = "송도점"
//            )
//        )
        getAllCenters()
    }

    fun getAllCenters(){
        viewModelScope.launch{
            try {
                val allCenters = repo.getAllCenters()
                _allCenters.value = allCenters
            } catch (e: Exception) {
                Log.e("failed to get all centers", e.message.toString())
            }
        }
    }
}