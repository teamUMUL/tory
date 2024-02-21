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

//    override suspend fun updateDEV(updatedLTO: LTOEntity) {
//        devRetrofit.updateD(updatedLTO)
//    }

    override suspend fun deleteDEV(selectedDEV: DomainResponse) {
        devRetrofit.deleteDomain(selectedDEV.id)
    }
}