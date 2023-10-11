package inu.thebite.tory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import inu.thebite.tory.database.Center.CenterEntity
import inu.thebite.tory.database.ChildClass.ChildClassEntity
import inu.thebite.tory.database.ChildInfo.ChildInfoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChildViewModel : ViewModel(){

    //센터
    private val _selectedCenter = MutableStateFlow<CenterEntity?>(null)
    val selectedCenter = _selectedCenter.asStateFlow()

    fun setSelectedCenter(centerEntity: CenterEntity) {

        _selectedCenter.value = centerEntity
    }

    fun clearSelectedCenter() {
        _selectedCenter.value = null
    }

    //방
    private val _selectedChildClass = MutableStateFlow<ChildClassEntity?>(null)
    val selectedChildClass = _selectedChildClass.asStateFlow()

    fun setSelectedChildClass(childClassEntity: ChildClassEntity) {

        _selectedChildClass.value = childClassEntity
    }

    fun clearSelectedChildClass() {
        _selectedChildClass.value = null
    }

    //아이
    private val _selectedChildInfo = MutableStateFlow<ChildInfoEntity?>(null)
    val selectedChildInfo = _selectedChildInfo.asStateFlow()

    fun setSelectedChildInfo(childInfoEntity: ChildInfoEntity) {

        _selectedChildInfo.value = childInfoEntity
    }

    fun clearSelectedChildInfo() {
        _selectedChildInfo.value = null
    }
}