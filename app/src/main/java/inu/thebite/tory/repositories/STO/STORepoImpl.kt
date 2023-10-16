package inu.thebite.tory.repositories.STO

import inu.thebite.tory.database.STO.STODatabase
import inu.thebite.tory.database.STO.STOEntity
import kotlinx.coroutines.flow.Flow

class STORepoImpl(private val database: STODatabase): STORepo {

    private val stoDao = database.stoDao()
    override suspend fun createSTO(sto: STOEntity) {
        stoDao.insertSTO(sto)
    }
    override suspend fun getAllSTOs(): Flow<List<STOEntity>>{
        return stoDao.getAllSTOs()
    }
    override suspend fun deleteAllData() {
        stoDao.deleteAllData()
    }

    override suspend fun getSTOById(stoId: Int): Flow<STOEntity> {
        return stoDao.getSTOById(stoId)
    }
    override suspend fun updateSTO(updatedSTO: STOEntity) {
        stoDao.updateSTO(updatedSTO)
    }

    override suspend fun deleteSTO(sto: STOEntity) {
        stoDao.deleteSTO(sto)
    }
    override suspend fun deleteSTOsByCriteria(
        childClass: String,
        childName: String,
        selectedDEV: String,
        selectedLTO: String
    ) {
        stoDao.deleteSTOsByCriteria(childClass, childName, selectedDEV, selectedLTO)
    }
}