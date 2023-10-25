package inu.thebite.tory.repositories.LTO

import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.lto.UpdateLtoStatusRequest

interface LTORepo {
    suspend fun createLTO(domain: DomainResponse, lto: LtoRequest)

    suspend fun updateLTOStatus(selectedLTO: LtoResponse, updateLtoStatusRequest: UpdateLtoStatusRequest)

    suspend fun updateLtoHitStatus(selectedLTO: LtoResponse, updateLtoStatusRequest: UpdateLtoStatusRequest)

    suspend fun updateLto(selectedLTO: LtoResponse, ltoRequest: LtoRequest)

    suspend fun getAllLTOs(): List<LtoResponse>

    suspend fun deleteLTO(selectedLTO: LtoResponse)

}