package inu.thebite.tory.repositories.Center

import inu.thebite.tory.database.Center.CenterDatabase
import inu.thebite.tory.database.Center.CenterEntity
import kotlinx.coroutines.flow.Flow

class CenterRepoImpl(private val database: CenterDatabase): CenterRepo {
    private val centerDao = database.centerDao()

    override suspend fun createCenter(center: CenterEntity) {
        centerDao.insertCenter(center)
    }

    override suspend fun getAllCenters(): Flow<List<CenterEntity>> {
        return centerDao.getAllCenters()
    }

    override suspend fun updateCenter(updatedCenter: CenterEntity) {
        centerDao.updateCenter(updatedCenter)

    }

    override suspend fun deleteCenter(center: CenterEntity) {
        centerDao.deleteCenter(center)

    }
}