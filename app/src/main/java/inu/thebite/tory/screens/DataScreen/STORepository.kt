package inu.thebite.tory.screens.DataScreen

class STORepository {
    private val stoList = mutableListOf<STO>()
    private var nextId = 1 // Initialize with a value

    fun createSTO(sto: STO) {
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
        ): List<STO> {
        return stoList.filter { sto ->
            (className == null || sto.className == className) &&
                    (childName == null || sto.childName == childName) &&
                    (selectedDEV == null || sto.selectedDEV == selectedDEV) &&
                    (selectedLTO == null || sto.selectedLTO == selectedLTO) &&
                    (stoName == null || sto.stoName == stoName)
        }
    }
    fun getSTOById(stoId: Int): STO? {
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

    fun updateSTO(updatedSTO: STO) {
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
}
