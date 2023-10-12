package inu.thebite.tory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.database.ChildClass.ChildClassEntity
import inu.thebite.tory.repositories.ChildClass.ChildClassRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

//반을 선택할 때 사용하는 ViewModel 선택과 조회만 가능함(삭제, 수정은 불가능)
class ChildClassSelectViewModel : ViewModel(), KoinComponent {
    private val repo: ChildClassRepo by inject()

    private val _allChildClasses : MutableStateFlow<List<ChildClassEntity>> = MutableStateFlow(emptyList())
    val allChildClasses = _allChildClasses.asStateFlow()

    private val _childClasses: MutableStateFlow<List<ChildClassEntity>?> = MutableStateFlow(null)
    val childClasses = _childClasses.asStateFlow()

    private val _selectedChildClass = MutableStateFlow<ChildClassEntity?>(null)
    val selectedChildClass = _selectedChildClass.asStateFlow()

    fun setSelectedChildClass(childClassEntity: ChildClassEntity) {

        _selectedChildClass.value = childClassEntity
    }

    fun clearSelectedChildClass() {
        _selectedChildClass.value = null
    }

    fun clearChildClasses(){
        _childClasses.value = null
    }
    init {
        getAllChildClasses()
    }

    private fun getAllChildClasses(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllChildClasses().collect{data ->
                _allChildClasses.update { data }
            }
        }
    }

    fun getChildClassesByCenterName(
        selectedCenterName: String,
    ){
        if(selectedCenterName.isEmpty()){
            _childClasses.update { null }
        }else{
            _childClasses.update {
                val filteredChildClasses = allChildClasses.value.filter {
                    it.centerName == selectedCenterName
                }
                filteredChildClasses
            }
        }
    }
}