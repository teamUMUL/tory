package inu.thebite.tory.repositories.LTO


import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoGraphResponse
import inu.thebite.tory.model.lto.LtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.lto.UpdateLtoStatusRequest
import inu.thebite.tory.retrofit.RetrofitApi
import retrofit2.Response

class LTORepoImpl: LTORepo {

    private val ltoRetrofit = RetrofitApi.apiService

    override suspend fun createLTO(selectedDEV: DomainResponse, newLTO: LtoRequest) {
        ltoRetrofit.addLto(domainId = selectedDEV.id, ltoRequest = newLTO)
    }

    override suspend fun updateLTOStatus(
        selectedLTO: LtoResponse,
        updateLtoStatusRequest: UpdateLtoStatusRequest
    ) {
        ltoRetrofit.updateLtoStatus(ltoId = selectedLTO.id, updateLtoStatusRequest = updateLtoStatusRequest)
    }

    override suspend fun updateLtoHitStatus(
        selectedLTO: LtoResponse,
        updateLtoStatusRequest: UpdateLtoStatusRequest
    ) {
        ltoRetrofit.updateLtoHitStatus(ltoId = selectedLTO.id, updateLtoStatusRequest = updateLtoStatusRequest)
    }

    override suspend fun updateLto(selectedLTO: LtoResponse, ltoRequest: LtoRequest) : Response<LtoResponse> {
        return ltoRetrofit.updateLto(ltoId = selectedLTO.id, ltoRequest = ltoRequest)
    }

    override suspend fun getLTOsByStudent(): List<LtoResponse> {
        return ltoRetrofit.getLtoListByStudent()
    }

    override suspend fun deleteLTO(selectedLTO: LtoResponse) {
        ltoRetrofit.deleteLto(ltoId = selectedLTO.id)
    }

    override suspend fun getLTOGraph(selectedLTO: LtoResponse): List<LtoGraphResponse> {
        return ltoRetrofit.getLtoGraph(ltoId = selectedLTO.id)
    }
}