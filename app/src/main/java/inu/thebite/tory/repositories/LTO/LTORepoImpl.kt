package inu.thebite.tory.repositories.LTO


import android.util.Log
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.AddLtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.lto.UpdateLtoStatusRequest
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.retrofit.RetrofitApi
import kotlinx.coroutines.flow.Flow

class LTORepoImpl: LTORepo {

    private val ltoRetrofit = RetrofitApi.apiService

    override suspend fun createLTO(selectedDEV: DomainResponse, newLTO: AddLtoRequest) {
        ltoRetrofit.addLto(domainId = selectedDEV.id, addLtoRequest = newLTO)
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

    override suspend fun getAllLTOs(): List<LtoResponse> {
        return ltoRetrofit.getLtoList()
    }

    override suspend fun deleteLTO(selectedLTO: LtoResponse) {
        ltoRetrofit.deleteLto(ltoId = selectedLTO.id)
    }
}