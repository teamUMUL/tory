package inu.thebite.tory.repositories.Center

import inu.thebite.tory.database.Center.CenterEntity
import kotlinx.coroutines.flow.Flow

interface CenterRepo {
    suspend fun createCenter(center: CenterEntity)
    suspend fun getAllCenters(): Flow<List<CenterEntity>>
    suspend fun updateCenter(updatedCenter: CenterEntity)
    suspend fun deleteCenter(center: CenterEntity)
}