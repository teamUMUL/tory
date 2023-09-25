package inu.thebite.tory.screens.DataScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

data class STO(
    var className: String,
    var childName: String,
    var selectedDEV: String,
    var selectedLTO: String,
    var stoId: Int,
    var stoName: String,
    var stoDescription: String,
    var stoTryNum: Int,
    var stoSuccessStandard: String,
    var stoMethod: String,
    var stoSchedule: String,
    var stoMemo: String,
    var stoState: Int,
    var gameResult: List<String>,
    var date: List<LocalDate>,
    var plusRatio: List<Float>,
    var minusRatio: List<Float>
)


class STOViewModel : ViewModel() {
    private val stoRepository = STORepository()

    // Initialize the stoId counter with 1
    private var nextStoId = 1

    // Function to generate and return the next available stoId
    private fun getNextStoId(): Int {
        val id = nextStoId
        nextStoId++
        return id
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

    // Function to create a new STO with an automatically generated stoId
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
        date: List<LocalDate>,
        plusRatio: List<Float>,
        minusRatio: List<Float>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newSTO = STO(
                stoId = getNextStoId(),
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
                gameResult= gameResult,
                date = date,
                plusRatio = plusRatio,
                minusRatio = minusRatio
            )
            stoRepository.createSTO(newSTO)
        }
    }

    fun getSTOsByCriteria(
        className: String? = null,
        childName: String? = null,
        selectedDEV: String? = null,
        selectedLTO: String? = null
    ) = stoRepository.getSTOsByCriteria(className, childName, selectedDEV, selectedLTO)

    fun getSTOIdByCriteria(
        childClass: String,
        childName: String,
        selectedDEV: String,
        selectedLTO: String,
        selectedSTO: String
    ): Int? {
        return stoRepository.getSTOIdByCriteria(
            className = childClass,
            childName = childName,
            selectedDEV = selectedDEV,
            selectedLTO = selectedLTO,
            stoName = selectedSTO
        )
    }
    fun getSTOById(stoId: Int): STO? {
        return stoRepository.getSTOById(stoId)
    }

    // Update an existing STO entry
    fun updateSTO(updatedSTO: STO) {
        viewModelScope.launch(Dispatchers.IO) {
            stoRepository.updateSTO(updatedSTO)
        }
    }

    // Delete an STO entry by its ID
    fun deleteSTO(stoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            stoRepository.deleteSTO(stoId)
        }
    }

    fun deleteSTOsByCriteria(
        childClass: String,
        childName: String,
        selectedDEV: String,
        selectedLTO: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            stoRepository.deleteSTOsByCriteria(childClass, childName, selectedDEV, selectedLTO)
        }
    }
}