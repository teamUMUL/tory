package inu.thebite.tory.repositories.LTO

import inu.thebite.tory.database.LTO.LTOEntity
import kotlinx.coroutines.flow.Flow

interface LTORepo {
    suspend fun createLTO(lto: LTOEntity)
    suspend fun getAllLTOs(): Flow<List<LTOEntity>>
    suspend fun updateLTO(updatedLTO: LTOEntity)
    suspend fun deleteLTO(lto: LTOEntity)
}