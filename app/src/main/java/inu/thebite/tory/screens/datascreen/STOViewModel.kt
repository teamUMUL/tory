package inu.thebite.tory.screens.datascreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.database.STO.STOEntity
import inu.thebite.tory.repositories.STO.STORepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.sql.Date

class STOViewModel : ViewModel(), KoinComponent {

    private val repo: STORepo by inject()

    private val _allSTOs : MutableStateFlow<List<STOEntity>> = MutableStateFlow(emptyList())
    val allSTOs = _allSTOs.asStateFlow()

    private val _stos: MutableStateFlow<List<STOEntity>?> = MutableStateFlow(null)
    val stos = _stos.asStateFlow()

    private val _stoId: MutableStateFlow<Int> = MutableStateFlow(-1)
    val stoId = _stoId.asStateFlow()
    init {
        getAllSTOs()
    }
    private fun getAllSTOs(){
        viewModelScope.launch(Dispatchers.IO) {
           repo.getAllSTOs().collect{data ->
               _allSTOs.update { data }
           }
        }
    }
    private val _selectedSTO = MutableStateFlow<STOEntity?>(null)
    val selectedSTO = _selectedSTO.asStateFlow()
    fun setSelectedSTO(stoEntity: STOEntity) {

        _selectedSTO.value = stoEntity
    }
    fun clearSelectedSTO() {
        _selectedSTO.value = null
    }
    val developZoneItems = listOf<String>(
        "1. 학습준비",
        "2. 매칭",
        "3. 동작모방",
        "4. 언어모방",
        "5. 변별",
        "6. 지시따라하기",
        "7. 요구하기",
        "8. 명명하기",
        "9. 인트라",
        "10. 가나다"
    )
    fun createSTO(
        className: String,
        childName: String,
        selectedDEV: String,
        selectedLTO: String,
        stoName: String,
        stoDescription: String,
        stoTryNum: Int,
        stoSuccessStandard: String,
        stoMethod: String,
        stoSchedule: String,
        stoMemo: String,
        stoState: Int,
        gameResult: List<String>,
        gameItems: List<String>,
        date: List<Date>,
        plusRatio: List<Float>,
        minusRatio: List<Float>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newSTOEntity = STOEntity(
                className = className,
                childName = childName,
                selectedDEV = selectedDEV,
                selectedLTO = selectedLTO,
                stoName = stoName,
                stoDescription = stoDescription,
                stoTryNum = stoTryNum,
                stoSuccessStandard = stoSuccessStandard,
                stoMethod = stoMethod,
                stoSchedule = stoSchedule,
                stoMemo = stoMemo,
                stoState = stoState,
                gameResult = gameResult,
                gameItems = gameItems,
                date = date,
                plusRatio = plusRatio,
                minusRatio = minusRatio
            )
            repo.createSTO(newSTOEntity)
        }
    }

    fun getSTOsByCriteria(
        className: String,
        childName: String,
        selectedDEV: String,
        selectedLTO: String,
    ){
        if(className.isEmpty() || childName.isEmpty() || selectedDEV.isEmpty() || selectedLTO.isEmpty()){
            _stos.update { null }
        }else{
            _stos.update {
                Log.d("FilteringResult", "Filtered Criteria: $className, $childName, $selectedDEV, $selectedLTO")
                val filteredSTOs = allSTOs.value.filter {
                    it.className == className &&
                    it.childName == childName &&
                    it.selectedDEV == selectedDEV &&
                    it.selectedLTO == selectedLTO
                }
                Log.d("FilteringResult", "Filtered STOs: $filteredSTOs")
                filteredSTOs
            }
        }
    }
    fun updateSTO(updatedSTOEntity: STOEntity) {
        // Implement the logic to update STO
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateSTO(updatedSTOEntity)
        }

    }

    fun deleteSTO(sto: STOEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteSTO(sto)
        }
    }

    fun deleteSTOsByCriteria(
        childClass: String,
        childName: String,
        selectedDEV: String,
        selectedLTO: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteSTOsByCriteria(childClass, childName, selectedDEV, selectedLTO)
        }
    }

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteAllData()
        }
    }
}

