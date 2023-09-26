package inu.thebite.tory.screens.DataScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.database.STOEntity
import inu.thebite.tory.repositories.STORepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.sql.Date

class STOViewModel : ViewModel(), KoinComponent {

    private val repo: STORepo by inject()

    private val _stos: MutableStateFlow<List<STOEntity>> = MutableStateFlow(emptyList())
    val stos = _stos.asStateFlow()

    private val _stoId: MutableStateFlow<Int> = MutableStateFlow(-1)
    val stoId = _stoId.asStateFlow()

    private val _sto: MutableStateFlow<STOEntity> = MutableStateFlow(
        STOEntity(
            stoId = -1,
            className = "",
            childName = "",
            selectedDEV = "",
            selectedLTO = "",
            stoName = "",
            stoDescription = "",
            stoTryNum = 15,
            stoSuccessStandard = "",
            stoMethod = "",
            stoSchedule = "",
            stoMemo = "",
            stoState = 1,
            gameResult = emptyList(),
            date = emptyList(),
            plusRatio = emptyList(),
            minusRatio = emptyList()
        )
    )
    val sto = _sto.asStateFlow()

    private val _selectedSTO = MutableStateFlow<STOEntity?>(null)
    val selectedSTO = _selectedSTO.asStateFlow()

    // Function to set the selected STO
    fun setSelectedSTO(stoEntity: STOEntity) {
        _selectedSTO.value = stoEntity
    }

    // Function to clear the selected STO
    fun clearSelectedSTO() {
        _selectedSTO.value = null
    }
    init {
        getSTOsByCriteria()

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
    // Function to create a new STO
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
                date = date,
                plusRatio = plusRatio,
                minusRatio = minusRatio
            )
            repo.createSTO(newSTOEntity)
        }
        getSTOsByCriteria(className, childName, selectedDEV, selectedLTO)
    }

    // Function to get STOs by criteria
    fun getSTOsByCriteria(
        className: String? = null,
        childName: String? = null,
        selectedDEV: String? = null,
        selectedLTO: String? = null,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getSTOsByCriteria(className, childName, selectedDEV, selectedLTO).collect{data ->
                _stos.update { data }
            }

        }
    }

    // Function to get STO ID by criteria
    fun getSTOIdByCriteria(
        className: String,
        childName: String,
        selectedDEV: String,
        selectedLTO: String,
        selectedSTO: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getSTOIdByCriteria(className, childName, selectedDEV, selectedLTO, selectedSTO).collect{data ->
                _stoId.update { data }
            }
        }
    }

    // Function to get STO by ID
    fun getSTOById(stoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getSTOById(stoId).collect{data ->
                _sto.update { data }
            }
        }
    }

    // Function to update an existing STO entry
    fun updateSTO(updatedSTOEntity: STOEntity) {
        // Implement the logic to update STO
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateSTO(updatedSTOEntity)
        }
    }

    // Function to delete an STO entry by its ID
    fun deleteSTO(sto: STOEntity) {
        // Implement the logic to delete STO by ID
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteSTO(sto)
        }
    }

    // Function to delete STOs by criteria
    fun deleteSTOsByCriteria(
        childClass: String,
        childName: String,
        selectedDEV: String,
        selectedLTO: String
    ) {
        // Implement the logic to delete STOs by criteria
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

