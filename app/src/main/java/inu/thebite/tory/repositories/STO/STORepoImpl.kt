package inu.thebite.tory.repositories.STO

import inu.thebite.tory.model.image.UpdateImageListRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.AddStoRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.UpdateStoRequest
import inu.thebite.tory.model.sto.UpdateStoStatusRequest
import inu.thebite.tory.retrofit.RetrofitApi
import kotlinx.coroutines.flow.Flow

class STORepoImpl: STORepo {

    private val stoRetrofit = RetrofitApi.apiService

    override suspend fun addSto(ltoInfo: LtoResponse, addStoRequest: AddStoRequest) {
        stoRetrofit.addSto(ltoId = ltoInfo.id, addStoRequest = addStoRequest)
    }

    override suspend fun updateStoStatus(
        stoInfo: StoResponse,
        updateStoStatusRequest: UpdateStoStatusRequest
    ) {
        stoRetrofit.updateStoStatus(stoId = stoInfo.id, updateStoStatusRequest = updateStoStatusRequest)
    }

    override suspend fun updateStoHitStatus(
        stoInfo: StoResponse,
        updateStoStatusRequest: UpdateStoStatusRequest
    ) {
        stoRetrofit.updateStoHitStatus(stoId = stoInfo.id, updateStoStatusRequest = updateStoStatusRequest)
    }

    override suspend fun updateSto(stoInfo: StoResponse, updateStoRequest: UpdateStoRequest) {
        stoRetrofit.updateSto(stoId = stoInfo.id, updateStoRequest = updateStoRequest)
    }

    override suspend fun updateImageList(
        stoInfo: StoResponse,
        updateImageListRequest: UpdateImageListRequest
    ) {
        stoRetrofit.updateImageList(stoId = stoInfo.id, updateImageListRequest = updateImageListRequest)
    }

    override suspend fun getStoList(): List<StoResponse> {
        return stoRetrofit.getStoList()
    }

    override suspend fun deleteSto(stoInfo: StoResponse) {
        stoRetrofit.deleteSto(stoId = stoInfo.id)
    }
}