package inu.thebite.tory.repositories.DEV

import inu.thebite.tory.model.domain.AddDomainRequest
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.retrofit.RetrofitApi
import retrofit2.Response

class DEVRepoImpl: DEVRepo {

    private val devRetrofit = RetrofitApi.apiService

    override suspend fun createDEV(newDEV: AddDomainRequest, centerId: Long) : Response<DomainResponse> {
        return devRetrofit.addDomain(addDomainRequest = newDEV, centerId = centerId)
    }

    override suspend fun getAllDEVs(centerId: Long): List<DomainResponse> {
        return devRetrofit.getDomainList(centerId = centerId)
    }

    override suspend fun updateDEV(domainId: Long, addDomainRequest: AddDomainRequest) : Response<DomainResponse> {
        return devRetrofit.updateDomain(domainId = domainId, addDomainRequest = addDomainRequest)
    }

    override suspend fun deleteDEV(domainId: Long) : Response<Boolean> {
        return devRetrofit.deleteDomain(domainId = domainId)
    }
}