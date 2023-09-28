package inu.thebite.tory.repositories.LTO

import inu.thebite.tory.database.LTO.LTODatabase
import inu.thebite.tory.database.LTO.LTOEntity
import inu.thebite.tory.database.STO.STOEntity
import kotlinx.coroutines.flow.Flow

class LTORepoImpl(private val database: LTODatabase): LTORepo {
    private val ltoDao = database.ltoDao()
    override suspend fun createLTO(lto: LTOEntity) {
        ltoDao.insertLTO(lto)
    }

    override suspend fun getAllLTOs(): Flow<List<LTOEntity>>{
        return ltoDao.getAllLTOs()
    }

    override suspend fun updateLTO(updatedLTO: LTOEntity) {
        ltoDao.updateLTO(updatedLTO)
    }

    override suspend fun deleteLTO(lto: LTOEntity) {
        ltoDao.deleteLTO(lto)
    }
}