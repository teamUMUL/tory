package inu.thebite.tory.repositories.DEV

import inu.thebite.tory.model.domain.AddDomainRequest
import inu.thebite.tory.model.domain.DomainResponse
import retrofit2.Response

interface DEVRepo {
    suspend fun createDEV(newDEV: AddDomainRequest, centerId: Long) : Response<DomainResponse>
    suspend fun getAllDEVs(centerId: Long): List<DomainResponse>
    suspend fun deleteDEV(selectedDEV: DomainResponse)
}