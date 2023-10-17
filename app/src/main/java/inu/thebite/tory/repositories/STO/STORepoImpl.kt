package inu.thebite.tory.repositories.STO

import inu.thebite.tory.model.sto.AddStoRequest
import inu.thebite.tory.model.sto.StoResponse
import kotlinx.coroutines.flow.Flow

class STORepoImpl: STORepo {

    override suspend fun createSTO(sto: AddStoRequest) {
    }
    override suspend fun getAllSTOs(): List<StoResponse>{
        return emptyList()
    }
//    override suspend fun deleteAllData() {
//        stoDao.deleteAllData()
//    }
//
//    override suspend fun getSTOById(stoId: Int): Flow<STOEntity> {
//        return stoDao.getSTOById(stoId)
//    }
//    override suspend fun updateSTO(updatedSTO: STOEntity) {
//        stoDao.updateSTO(updatedSTO)
//    }
//
//    override suspend fun deleteSTO(sto: STOEntity) {
//        stoDao.deleteSTO(sto)
//    }
//    override suspend fun deleteSTOsByCriteria(
//        childClass: String,
//        childName: String,
//        selectedDEV: String,
//        selectedLTO: String
//    ) {
//        stoDao.deleteSTOsByCriteria(childClass, childName, selectedDEV, selectedLTO)
//    }
}