package inu.thebite.tory.screens.teachingboard.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.repositories.ChildInfo.ChildInfoRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    //임시로 선택된 아이
    private val _tempSelectedChildInfo = MutableStateFlow<StudentResponse?>(null)
    val tempSelectedChildInfo = _tempSelectedChildInfo.asStateFlow()

    fun setTempSelectedChildInfo(childInfoEntity: StudentResponse) {

        _tempSelectedChildInfo.value = childInfoEntity
    }

    fun clearTempSelectedChildInfo() {
        _tempSelectedChildInfo.value = null
    }

    init {
//        setSelectedChildInfo(
//            childInfoEntity = StudentResponse(
//                id = 1L,
//                name = "김토리",
//                birth = "2023/11/01",
//                etc = "",
//                parentName = "김토리부모",
//                startDate = "2023/11/01",
//                endDate = "2023/11/31",
//                registerDate = "2023/11/01",
//                childClass = ChildClassResponse(
//                    id = 1L,
//                    name = "오전반",
//                    center = CenterResponse(
//                        id = 1L,
//                        name = "송도점"
//                    )
//                )
//            )
//        )
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
                    it.childClass.id == selectedClass.id
                }
                filteredChildInfos
            }
        }else{
            _childInfos.update { null }
        }
    }
}