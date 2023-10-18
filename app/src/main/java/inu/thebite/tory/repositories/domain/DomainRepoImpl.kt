package inu.thebite.tory.repositories.domain

import inu.thebite.tory.model.domain.AddDomainRequest
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.retrofit.RetrofitApi

class DomainRepoImpl : DomainRepo {

    private val domainRetrofit = RetrofitApi.apiService

    override suspend fun addDomain(request: AddDomainRequest) {
        domainRetrofit.addDomain(addDomainRequest = request)
    }

    override suspend fun getDomainList(): List<DomainResponse> {
        return domainRetrofit.getDomainList()
    }

    override suspend fun deleteDomain(domainInfo: DomainResponse) {
        domainRetrofit.deleteDomain(domainId = domainInfo.id)
    }
}