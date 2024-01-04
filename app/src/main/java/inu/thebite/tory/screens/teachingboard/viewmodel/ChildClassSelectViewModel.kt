package inu.thebite.tory.screens.teachingboard.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.repositories.ChildClass.ChildClassRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

//반을 선택할 때 사용하는 ViewModel 선택과 조회만 가능함(삭제, 수정은 불가능)
//반을 선택할 때 사용하는 ViewModel 선택과 조회만 가능함(삭제, 수정은 불가능)
class ChildClassSelectViewModel : ViewModel() {
    private val repo: ChildClassRepoImpl = ChildClassRepoImpl()

    private val _allChildClasses : MutableStateFlow<List<ChildClassResponse>?> = MutableStateFlow(null)
    val allChildClasses = _allChildClasses.asStateFlow()

    private val _childClasses: MutableStateFlow<List<ChildClassResponse>?> = MutableStateFlow(null)
    val childClasses = _childClasses.asStateFlow()

    //선택된 반
    private val _selectedChildClass = MutableStateFlow<ChildClassResponse?>(null)
    val selectedChildClass = _selectedChildClass.asStateFlow()

    fun setSelectedChildClass(childClassEntity: ChildClassResponse) {

        _selectedChildClass.value = childClassEntity
    }

    fun clearSelectedChildClass() {
        _selectedChildClass.value = null
    }

    //임시로 선택된 반
    private val _tempSelectedChildClass = MutableStateFlow<ChildClassResponse?>(null)
    val tempSelectedChildClass = _tempSelectedChildClass.asStateFlow()

    fun setTempSelectedChildClass(childClassEntity: ChildClassResponse) {

        _tempSelectedChildClass.value = childClassEntity
    }

    fun clearTempSelectedChildClass() {
        _tempSelectedChildClass.value = null
    }

    fun clearChildClasses(){
//        _childClasses.value = null
    }
    init {
//        setSelectedChildClass(
//            childClassEntity = ChildClassResponse(
//                id = 1L,
//                name = "오전반",
//                center = CenterResponse(
//                    id = 1L,
//                    name = "송도점"
//                )
//            )
//        )
//        getAllChildClasses()
    }

    fun getAllChildClasses(centerId : Long){
        viewModelScope.launch{
            try {
                val allChildClasses = repo.getAllChildClasses(centerId = centerId)
                _allChildClasses.value = allChildClasses
            } catch (e: Exception) {
                Log.e("failed to get all classes", e.message.toString())
            }
        }
    }

    fun getChildClassesByCenter(
        selectedCenter: CenterResponse,
    ){
//        if(selectedCenter.isNotNull()){
//            _childClasses.update {
//                val filteredChildClasses = allChildClasses.value!!.filter {
//                    it.center.id == selectedCenter.id
//                }
//                filteredChildClasses
//            }
//        }else{
//            _childClasses.update { null }
//        }
    }
}