package inu.thebite.tory.repositories.DEV

import inu.thebite.tory.model.domain.AddDomainRequest
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.AddLtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.repositories.LTO.LTORepo
import inu.thebite.tory.retrofit.RetrofitApi

class DEVRepoImpl: DEVRepo {

    private val devRetrofit = RetrofitApi.apiService

    override suspend fun createLTO(dev: AddDomainRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllLTOs(): List<DomainResponse> {
        TODO("Not yet implemented")
    }

//    override suspend fun updateLTO(updatedLTO: LTOEntity) {
//        ltoDao.updateLTO(updatedLTO)
//    }
//
//    override suspend fun deleteLTO(lto: LTOEntity) {
//        ltoDao.deleteLTO(lto)
//    }
}