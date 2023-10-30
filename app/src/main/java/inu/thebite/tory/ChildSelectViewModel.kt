package inu.thebite.tory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.model.student.AddStudentRequest
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.repositories.ChildInfo.ChildInfoRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import java.lang.Exception


//아이를 선택할 때 사용하는 ViewModel 선택과 조회만 가능함(삭제, 수정은 불가능)
class ChildSelectViewModel : ViewModel() {
    private val repo: ChildInfoRepoImpl = ChildInfoRepoImpl()

    private val _allChildInfos: MutableStateFlow<List<StudentResponse>?> = MutableStateFlow(null)
    val allChildInfos = _allChildInfos.asStateFlow()


    private val _childInfos: MutableStateFlow<List<StudentResponse>?> = MutableStateFlow(null)
    val childInfos = _childInfos.asStateFlow()

    private val _selectedChildInfo = MutableStateFlow<StudentResponse?>(null)
    val selectedChildInfo = _selectedChildInfo.asStateFlow()

    fun setSelectedChildInfo(childInfoEntity: StudentResponse) {

        _selectedChildInfo.value = childInfoEntity
    }

    fun clearSelectedChildInfo() {
        _selectedChildInfo.value = null
    }

    fun clearChildInfos(){
        _childInfos.value = null
    }
    init {
        getAllChildInfos()
    }

    fun getAllChildInfos(){
        viewModelScope.launch{
            try {
                val allChildInfos = repo.getAllChildInfos()
                _allChildInfos.value = allChildInfos
            } catch (e: Exception) {
                Log.e("failed to get all students", e.message.toString())
            }
        }
    }

    fun getChildInfosByClass(
        selectedClass: ChildClassResponse,
    ){
        if(selectedClass.isNotNull()){
            _childInfos.update {
                val filteredChildInfos = allChildInfos.value!!.filter {
                    it.id == selectedClass.id
                }
                filteredChildInfos
            }
        }else{
            _childInfos.update { null }
        }
    }
}