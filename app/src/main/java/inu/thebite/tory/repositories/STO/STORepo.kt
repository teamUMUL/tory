package inu.thebite.tory.repositories.STO

import inu.thebite.tory.model.sto.AddStoRequest
import inu.thebite.tory.model.sto.StoResponse
import kotlinx.coroutines.flow.Flow

interface STORepo {
    suspend fun createSTO(sto: AddStoRequest)
    suspend fun getAllSTOs(): List<StoResponse>
//    suspend fun updateSTO(updatedSTO: STOEntity)
//    suspend fun deleteSTO(sto: STOEntity)
//    suspend fun deleteSTOsByCriteria(
//        childClass: String,
//        childName: String,
//        selectedDEV: String,
//        selectedLTO: String
//    )
}