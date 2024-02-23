package inu.thebite.tory.repositories.LTO

import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.DevelopTypeRequest
import inu.thebite.tory.model.lto.DevelopTypeResponse
import inu.thebite.tory.model.lto.LtoGraphResponse
import inu.thebite.tory.model.lto.LtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.lto.UpdateLtoStatusRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

interface LTORepo {
    suspend fun addLTO(domain: DomainResponse, lto: LtoRequest, studentId: Long) : Response<LtoResponse>

    suspend fun updateLTOStatus(selectedLTO: LtoResponse, updateLtoStatusRequest: UpdateLtoStatusRequest) : Response<LtoResponse>

    suspend fun updateLtoHitStatus(selectedLTO: LtoResponse, updateLtoStatusRequest: UpdateLtoStatusRequest) : Response<LtoResponse>

    suspend fun updateLto(selectedLTO: LtoResponse, ltoRequest: LtoRequest) : Response<LtoResponse>

    suspend fun getAllLTOs(studentId : Long) : List<LtoResponse>

    suspend fun getLTOsByStudent(domainId : Long, studentId: Long): List<LtoResponse>

    suspend fun deleteLTO(selectedLTO: LtoResponse) : Response<Boolean>

    suspend fun getLTOGraph(selectedLTO : LtoResponse) : List<LtoGraphResponse>

    suspend fun updateDevelopType(ltoId: Long, developTypeRequest: DevelopTypeRequest) : Response<DevelopTypeResponse>

    suspend fun removeDevelopType(ltoId: Long, developTypeRequest: DevelopTypeRequest) : Response<DevelopTypeResponse>
}