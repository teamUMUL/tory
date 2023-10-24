package inu.thebite.tory.repositories.domain

import inu.thebite.tory.model.domain.AddDomainRequest
import inu.thebite.tory.model.domain.DomainResponse

interface DomainRepo {

    suspend fun addDomain(request: AddDomainRequest)

    suspend fun getDomainList() : List<DomainResponse>

    suspend fun deleteDomain(domainInfo: DomainResponse)
}