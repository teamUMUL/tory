package inu.thebite.tory

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.model.center.CenterRequest
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.repositories.Center.CenterRepo
import inu.thebite.tory.repositories.Center.CenterRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import java.lang.Exception

//센터를 선택할 때 사용하는 ViewModel 선택과 조회만 가능함(삭제, 수정은 불가능)
class CenterSelectViewModel : ViewModel() {
    private val repo: CenterRepoImpl = CenterRepoImpl()


    private val _allCenters = MutableLiveData<List<CenterResponse>>()
    val allCenters: LiveData<List<CenterResponse>> = _allCenters

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

    private fun getAllCenters(){
        viewModelScope.launch {
            try {
                val allCenters = repo.getAllCenters()
                _allCenters.value = allCenters
            } catch (e: Exception) {
                Log.e("forEach문", e.message.toString())
            }
        }
    }

}