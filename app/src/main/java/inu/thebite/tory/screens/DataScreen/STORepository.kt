package inu.thebite.tory.screens.DataScreen

import inu.thebite.tory.database.STOEntity

class STORepository {
    private val stoList = mutableListOf<STOEntity>()
    private var nextId = 1 // Initialize with a value

    fun createSTO(sto: STOEntity) {
        val newSTO = sto.copy(stoId = nextId++)
        stoList.add(newSTO)
    }

    fun getAllSTOs() = stoList.toList()

    fun getSTOsByCriteria(
        className: String? = null,
        childName: String? = null,
        selectedDEV: String? = null,
        selectedLTO: String? = null,
        stoName: String? = null,
        ): List<STOEntity> {
        return stoList.filter { sto ->
            (className == null || sto.className == className) &&
                    (childName == null || sto.childName == childName) &&
                    (selectedDEV == null || sto.selectedDEV == selectedDEV) &&
                    (selectedLTO == null || sto.selectedLTO == selectedLTO) &&
                    (stoName == null || sto.stoName == stoName)
        }
    }
    fun getSTOById(stoId: Int): STOEntity? {
        return stoList.find { it.stoId == stoId }
    }
    // Get STO ID by criteria
    fun getSTOIdByCriteria(
        className: String? = null,
        childName: String? = null,
        selectedDEV: String? = null,
        selectedLTO: String? = null,
        stoName: String? = null
    ): Int? {
        val matchingSTO = stoList.find { sto ->
            (className == null || sto.className == className) &&
                    (childName == null || sto.childName == childName) &&
                    (selectedDEV == null || sto.selectedDEV == selectedDEV) &&
                    (selectedLTO == null || sto.selectedLTO == selectedLTO) &&
                    (stoName == null || sto.stoName == stoName)
        }

        return matchingSTO?.stoId
    }

    fun updateSTO(updatedSTO: STOEntity) {
        val index = stoList.indexOfFirst { it.stoId == updatedSTO.stoId }
        if (index != -1) {
            stoList[index] = updatedSTO
        }
    }

    fun deleteSTO(stoId: Int) {
        val index = stoList.indexOfFirst { it.stoId == stoId }
        if (index != -1) {
            stoList.removeAt(index)
        }
    }

    fun deleteSTOsByCriteria(
        childClass: String,
        childName: String,
        selectedDEV: String,
        selectedLTO: String
    ) {
        val matchingSTOs = stoList.filter { sto ->
            (childClass == null || sto.className == childClass) &&
            (childName == null || sto.childName == childName) &&
            (selectedDEV == null || sto.selectedDEV == selectedDEV) &&
            (selectedLTO == null || sto.selectedLTO == selectedLTO)
        }

        matchingSTOs.forEach { sto ->
            stoList.remove(sto)
        }
    }
}
