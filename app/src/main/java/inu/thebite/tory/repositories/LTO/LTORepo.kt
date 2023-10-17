package inu.thebite.tory.repositories.LTO

import inu.thebite.tory.model.lto.AddLtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import kotlinx.coroutines.flow.Flow

interface LTORepo {
    suspend fun createLTO(lto: AddLtoRequest)
    suspend fun getAllLTOs(): List<LtoResponse>
//    suspend fun updateLTO(updatedLTO: LTOEntity)
//    suspend fun deleteLTO(lto: LTOEntity)
}