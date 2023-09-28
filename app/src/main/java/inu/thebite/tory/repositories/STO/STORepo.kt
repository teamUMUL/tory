package inu.thebite.tory.repositories.STO

import inu.thebite.tory.database.STO.STOEntity
import kotlinx.coroutines.flow.Flow

interface STORepo {
    suspend fun createSTO(sto: STOEntity)
    suspend fun getAllSTOs(): Flow<List<STOEntity>>
//    suspend fun getSTOsByCriteria(
//        className: String,
//        childName: String,
//        selectedDEV: String,
//        selectedLTO: String,
//    ): Flow<List<STOEntity>>
    suspend fun deleteAllData()
    suspend fun getSTOById(stoId: Int): Flow<STOEntity>
//    suspend fun getSTOIdByCriteria(
//        className: String? = null,
//        childName: String? = null,
//        selectedDEV: String? = null,
//        selectedLTO: String? = null,
//        stoName: String? = null
//    ): Flow<Int>

    suspend fun updateSTO(updatedSTO: STOEntity)
    suspend fun deleteSTO(sto: STOEntity)
    suspend fun deleteSTOsByCriteria(
        childClass: String,
        childName: String,
        selectedDEV: String,
        selectedLTO: String
    )
}