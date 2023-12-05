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

    override suspend fun addLTO(selectedDEV: DomainResponse, newLTO: LtoRequest, studentId: Long) : Response<LtoResponse> {
        return ltoRetrofit.addLto(domainId = selectedDEV.id, ltoRequest = newLTO, studentId = studentId)
    }

    override suspend fun updateLTOStatus(
        selectedLTO: LtoResponse,
        updateLtoStatusRequest: UpdateLtoStatusRequest
    ) : Response<LtoResponse> {
        return ltoRetrofit.updateLtoStatus(ltoId = selectedLTO.id, updateLtoStatusRequest = updateLtoStatusRequest)
    }

    override suspend fun updateLtoHitStatus(
        selectedLTO: LtoResponse,
        updateLtoStatusRequest: UpdateLtoStatusRequest
    ) : Response<LtoResponse> {
        return ltoRetrofit.updateLtoHitStatus(ltoId = selectedLTO.id, updateLtoStatusRequest = updateLtoStatusRequest)
    }

    override suspend fun updateLto(selectedLTO: LtoResponse, ltoRequest: LtoRequest) : Response<LtoResponse> {
        return ltoRetrofit.updateLto(ltoId = selectedLTO.id, ltoRequest = ltoRequest)
    }

    override suspend fun getAllLTOs(studentId : Long): List<LtoResponse> {
        return ltoRetrofit.getLtoList(studentId = studentId)
    }

    override suspend fun getLTOsByStudent(domainId : Long): List<LtoResponse> {
        return ltoRetrofit.getLtoListByStudent(domainId = domainId)
    }

    override suspend fun deleteLTO(selectedLTO: LtoResponse) : Response<Boolean> {
        return ltoRetrofit.deleteLto(ltoId = selectedLTO.id)
    }

    override suspend fun getLTOGraph(selectedLTO: LtoResponse): List<LtoGraphResponse> {
        return ltoRetrofit.getLtoGraph(ltoId = selectedLTO.id)
    }
}