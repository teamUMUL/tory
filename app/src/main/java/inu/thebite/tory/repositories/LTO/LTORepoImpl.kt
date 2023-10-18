package inu.thebite.tory.repositories.LTO


import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.AddLtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.lto.UpdateLtoStatusRequest
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.retrofit.RetrofitApi
import kotlinx.coroutines.flow.Flow

class LTORepoImpl: LTORepo {

    private val ltoRetrofit = RetrofitApi.apiService

    override suspend fun createLTO(domain: DomainResponse, lto: AddLtoRequest) {
        ltoRetrofit.addLto(domainId = domain.id, addLtoRequest = lto)
    }

    override suspend fun updateLTOStatus(
        ltoInfo: LtoResponse,
        updateLtoStatusRequest: UpdateLtoStatusRequest
    ) {
        ltoRetrofit.updateLtoStatus(ltoId = ltoInfo.id, updateLtoStatusRequest = updateLtoStatusRequest)
    }

    override suspend fun updateLtoHitStatus(
        ltoInfo: LtoResponse,
        updateLtoStatusRequest: UpdateLtoStatusRequest
    ) {
        ltoRetrofit.updateLtoHitStatus(ltoId = ltoInfo.id, updateLtoStatusRequest = updateLtoStatusRequest)
    }

    override suspend fun getAllLTOs(): List<LtoResponse> {
        return ltoRetrofit.getLtoList()
    }

    override suspend fun deleteLTO(ltoInfo: LtoResponse) {
        ltoRetrofit.deleteLto(ltoId = ltoInfo.id)
    }
}