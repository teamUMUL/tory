package inu.thebite.tory.repositories.DEV

import inu.thebite.tory.model.domain.AddDomainRequest
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.retrofit.RetrofitApi

class DEVRepoImpl: DEVRepo {

    private val devRetrofit = RetrofitApi.apiService

    override suspend fun createDEV(newDEV: AddDomainRequest) {
        devRetrofit.addDomain(addDomainRequest = newDEV)
    }

    override suspend fun getAllDEVs(): List<DomainResponse> {
        return devRetrofit.getDomainList()
    }

//    override suspend fun updateDEV(updatedLTO: LTOEntity) {
//        devRetrofit.updateD(updatedLTO)
//    }

    override suspend fun deleteDEV(selectedDEV: DomainResponse) {
        devRetrofit.deleteDomain(selectedDEV.id)
    }
}