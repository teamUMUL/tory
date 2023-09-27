package inu.thebite.tory.repositories

import inu.thebite.tory.database.STODao
import inu.thebite.tory.database.STODatabase
import inu.thebite.tory.database.STOEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class STORepoImpl(private val database: STODatabase):STORepo {

    private val stoDao = database.stoDao()

    override suspend fun createSTO(sto: STOEntity) {
        stoDao.insertSTO(sto)
    }

    override suspend fun getAllSTOs(): Flow<List<STOEntity>>{
        return stoDao.getAllSTOs()
    }

//    override suspend fun getSTOsByCriteria(
//        className: String,
//        childName: String,
//        selectedDEV: String,
//        selectedLTO: String,
//    ): Flow<List<STOEntity>> {
//        return stoDao.getSTOsByCriteria(className,childName,selectedDEV,selectedLTO)
//    }

    override suspend fun deleteAllData() {
        stoDao.deleteAllData()
    }

    override suspend fun getSTOById(stoId: Int): Flow<STOEntity> {
        return stoDao.getSTOById(stoId)
    }

//    override suspend fun getSTOIdByCriteria(
//        className: String?,
//        childName: String?,
//        selectedDEV: String?,
//        selectedLTO: String?,
//        stoName: String?
//    ): Flow<Int> {
//        return stoDao.getSTOIdByCriteria(className, childName, selectedDEV, selectedLTO, stoName)
//    }

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