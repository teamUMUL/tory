package inu.thebite.tory.repositories

import inu.thebite.tory.database.STOEntity
import kotlinx.coroutines.flow.Flow

interface STORepo {
    suspend fun createSTO(sto: STOEntity)
//    suspend fun getAllSTOs(): Flow<List<STOEntity>>
    suspend fun getSTOsByCriteria(
        className: String? = null,
        childName: String? = null,
        selectedDEV: String? = null,
        selectedLTO: String? = null,
    ): Flow<List<STOEntity>>
    suspend fun deleteAllData()
    suspend fun getSTOById(stoId: Int): Flow<STOEntity>
    suspend fun getSTOIdByCriteria(
        className: String? = null,
        childName: String? = null,
        selectedDEV: String? = null,
        selectedLTO: String? = null,
        stoName: String? = null
    ): Flow<Int>

    suspend fun updateSTO(updatedSTO: STOEntity)
    suspend fun deleteSTO(sto: STOEntity)
    suspend fun deleteSTOsByCriteria(
        childClass: String,
        childName: String,
        selectedDEV: String,
        selectedLTO: String
    )
}