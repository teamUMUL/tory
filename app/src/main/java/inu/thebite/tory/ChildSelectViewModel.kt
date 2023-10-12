package inu.thebite.tory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.database.ChildInfo.ChildInfoEntity
import inu.thebite.tory.repositories.ChildInfo.ChildInfoRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject


//아이를 선택할 때 사용하는 ViewModel 선택과 조회만 가능함(삭제, 수정은 불가능)
class ChildSelectViewModel : ViewModel(), KoinComponent {
    private val repo: ChildInfoRepo by inject()

    private val _allChildInfos : MutableStateFlow<List<ChildInfoEntity>> = MutableStateFlow(emptyList())
    val allChildInfos = _allChildInfos.asStateFlow()

    private val _childInfos: MutableStateFlow<List<ChildInfoEntity>?> = MutableStateFlow(null)
    val childInfos = _childInfos.asStateFlow()

    private val _selectedChildInfo = MutableStateFlow<ChildInfoEntity?>(null)
    val selectedChildInfo = _selectedChildInfo.asStateFlow()

    fun setSelectedChildInfo(childInfoEntity: ChildInfoEntity) {

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

    private fun getAllChildInfos(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllChildInfos().collect{data ->
                _allChildInfos.update { data }
            }
        }
    }

    fun getChildInfosByCenterNameAndClassName(
        selectedCenterName: String,
        selectedClassName: String?,
    ){
        if(selectedClassName == null ){
            _childInfos.update { null }
        }else{
            _childInfos.update {
                val filteredChildClasses = allChildInfos.value.filter {
                    it.centerName == selectedCenterName &&
                            it.className == selectedClassName
                }
                filteredChildClasses
            }
        }
    }
}