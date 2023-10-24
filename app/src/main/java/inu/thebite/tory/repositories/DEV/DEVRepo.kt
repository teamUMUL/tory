package inu.thebite.tory.repositories.DEV

import inu.thebite.tory.model.domain.AddDomainRequest
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.AddLtoRequest
import inu.thebite.tory.model.lto.LtoResponse

interface DEVRepo {
    suspend fun createDEV(newDEV: AddDomainRequest)
    suspend fun getAllDEVs(): List<DomainResponse>
    suspend fun deleteDEV(selectedDEV: DomainResponse)
}