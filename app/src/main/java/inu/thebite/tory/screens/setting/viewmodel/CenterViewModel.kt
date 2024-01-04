package inu.thebite.tory.screens.setting.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.model.center.CenterRequest
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.repositories.Center.CenterRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    fun clearAll(){
        _allCenters.update { null }
        _centers.update { null }
        _selectedCenter.update { null }
    }
    init {
        getAllCenters()
        observeAllCenters()
    }

    private fun observeAllCenters() {
        viewModelScope.launch {
            allCenters.onEach { allCenters ->
                updateSelectedCenter(allCenters)
            }.collect()
        }
    }

    private fun updateSelectedCenter(allCenters: List<CenterResponse>?) {
        allCenters?.let { allCenters ->
            _selectedCenter.update {
                val foundCenter = allCenters.find { center ->
                    selectedCenter.value?.id == center.id
                }
                foundCenter
            }
        }
    }

    fun getAllCenters(){
        viewModelScope.launch {
            try {
                val allCenters = repo.getAllCenters()
                Log.e("가지고 온 센터", allCenters.toString())
                _allCenters.update {
                    allCenters
                }
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
                val response = repo.createCenter(center = newCenter)

                if (response.isSuccessful) {
                    val newCenterResponse = response.body() ?: throw Exception("Center 정보가 비어있습니다.")
                    _allCenters.update { currentCenters ->
                        currentCenters?.let {
                            // 현재 Center 리스트가 null이 아니면 새 Center를 추가
                            it + newCenterResponse
                        } ?: listOf(newCenterResponse) // 현재 Center 리스트가 null이면 새 리스트를 생성
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("Center 추가 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to add Center", e.message.toString())
            }
        }

    }

    fun updateCenter(selectedCenter: CenterResponse, updateCenter: CenterRequest) {
        viewModelScope.launch {
            try {
                val response = repo.updateCenter(selectedCenter = selectedCenter, updateCenter = updateCenter)

                if (response.isSuccessful) {
                    val updatedCenter = response.body() ?: throw Exception("Center 정보가 비어있습니다.")
                    _allCenters.update {
                        allCenters.value?.let { allSTOs ->
                            allSTOs.map { if (it.id == updatedCenter.id) updatedCenter else it }
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("Center 업데이트 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to update Center", e.message.toString())
            }
        }
    }

    fun deleteCenter(centerEntity: CenterResponse) {
        viewModelScope.launch {
            try {
                val response = repo.deleteCenter(centerEntity)

                if (response.isSuccessful) {
                    val isDeleted = response.body() ?: throw Exception("Center 삭제 정보가 비어있습니다.")
                    if (isDeleted) {
                        _allCenters.update { currentCenters ->
                            currentCenters?.filterNot { it.id == centerEntity.id }
                        }
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("Center 식제 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to delete Center", e.message.toString())
            }
        }
    }
}