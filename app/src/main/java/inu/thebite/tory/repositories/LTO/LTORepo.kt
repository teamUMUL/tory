package inu.thebite.tory.repositories.LTO

import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoGraphResponse
import inu.thebite.tory.model.lto.LtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.lto.UpdateLtoStatusRequest
import retrofit2.Response

interface LTORepo {
    suspend fun createLTO(domain: DomainResponse, lto: LtoRequest) : Response<LtoResponse>

    suspend fun updateLTOStatus(selectedLTO: LtoResponse, updateLtoStatusRequest: UpdateLtoStatusRequest) : Response<LtoResponse>

    suspend fun updateLtoHitStatus(selectedLTO: LtoResponse, updateLtoStatusRequest: UpdateLtoStatusRequest) : Response<LtoResponse>

    suspend fun updateLto(selectedLTO: LtoResponse, ltoRequest: LtoRequest) : Response<LtoResponse>

    suspend fun getLTOsByStudent(domainId : Long): List<LtoResponse>

    suspend fun deleteLTO(selectedLTO: LtoResponse) : Response<Boolean>

    suspend fun getLTOGraph(selectedLTO : LtoResponse) : List<LtoGraphResponse>
}