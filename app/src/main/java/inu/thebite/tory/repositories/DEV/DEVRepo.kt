package inu.thebite.tory.repositories.DEV

import inu.thebite.tory.model.domain.AddDomainRequest
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.AddLtoRequest
import inu.thebite.tory.model.lto.LtoResponse

interface DEVRepo {
    suspend fun createLTO(dev: AddDomainRequest)
    suspend fun getAllLTOs(): List<DomainResponse>
//    suspend fun updateLTO(updatedLTO: LTOEntity)
//    suspend fun deleteLTO(lto: LTOEntity)
}