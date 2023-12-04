package inu.thebite.tory.repositories.LTO

import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoGraphResponse
import inu.thebite.tory.model.lto.LtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.lto.UpdateLtoStatusRequest
import retrofit2.Response

interface LTORepo {
    suspend fun createLTO(domain: DomainResponse, lto: LtoRequest)

    suspend fun updateLTOStatus(selectedLTO: LtoResponse, updateLtoStatusRequest: UpdateLtoStatusRequest)

    suspend fun updateLtoHitStatus(selectedLTO: LtoResponse, updateLtoStatusRequest: UpdateLtoStatusRequest)

    suspend fun updateLto(selectedLTO: LtoResponse, ltoRequest: LtoRequest) : Response<LtoResponse>

    suspend fun getLTOsByStudent(): List<LtoResponse>

    suspend fun deleteLTO(selectedLTO: LtoResponse)

    suspend fun getLTOGraph(selectedLTO : LtoResponse) : List<LtoGraphResponse>
}