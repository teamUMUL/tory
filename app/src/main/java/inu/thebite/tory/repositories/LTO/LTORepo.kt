package inu.thebite.tory.repositories.LTO

import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.AddLtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.lto.UpdateLtoStatusRequest
import kotlinx.coroutines.flow.Flow

interface LTORepo {
    suspend fun createLTO(domain: DomainResponse, lto: AddLtoRequest)

    suspend fun updateLTOStatus(selectedLTO: LtoResponse, updateLtoStatusRequest: UpdateLtoStatusRequest)

    suspend fun updateLtoHitStatus(selectedLTO: LtoResponse, updateLtoStatusRequest: UpdateLtoStatusRequest)

    suspend fun getAllLTOs(): List<LtoResponse>

    suspend fun deleteLTO(selectedLTO: LtoResponse)

}