package inu.thebite.tory

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.database.Center.CenterEntity
import inu.thebite.tory.repositories.Center.CenterRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject

//센터를 선택할 때 사용하는 ViewModel 선택과 조회만 가능함(삭제, 수정은 불가능)
class CenterSelectViewModel : ViewModel(), KoinComponent {
    private val repo: CenterRepo by inject()

    private val _allCenters : MutableStateFlow<List<CenterEntity>> = MutableStateFlow(emptyList())
    val allCenters = _allCenters.asStateFlow()

    private val _centers: MutableStateFlow<List<CenterEntity>?> = MutableStateFlow(null)
    val centers = _centers.asStateFlow()

    private val _selectedCenter = MutableStateFlow<CenterEntity?>(null)
    val selectedCenter = _selectedCenter.asStateFlow()

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
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllCenters().collect{data ->
                _allCenters.update { data }
            }
        }
    }
}