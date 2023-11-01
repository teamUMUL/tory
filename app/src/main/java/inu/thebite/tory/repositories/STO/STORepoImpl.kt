package inu.thebite.tory.repositories.STO

import inu.thebite.tory.model.image.UpdateImageListRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.point.AddPointRequest
import inu.thebite.tory.model.point.DeletePointRequest
import inu.thebite.tory.model.sto.AddStoRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.UpdateStoRequest
import inu.thebite.tory.model.sto.UpdateStoRoundRequest
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

    override suspend fun getPointList(selectedSTO: StoResponse): List<String> {
        return stoRetrofit.getPointList(selectedSTO.id)
    }

    override suspend fun addPoint(selectedSTO: StoResponse, addPointRequest: AddPointRequest) {
        stoRetrofit.addPoint(stoId = selectedSTO.id, addPointRequest = addPointRequest)
    }

    override suspend fun addRound(selectedSTO: StoResponse, updateStoRoundRequest: UpdateStoRoundRequest) {
        stoRetrofit.updateStoRound(stoId = selectedSTO.id, updateStoRoundRequest = updateStoRoundRequest)
    }
    override suspend fun addRoundHit(selectedSTO: StoResponse, updateStoRoundRequest: UpdateStoRoundRequest) {
        stoRetrofit.updateStoHitRound(stoId = selectedSTO.id, updateStoRoundRequest = updateStoRoundRequest)
    }

    override suspend fun deletePoint(selectedSTO: StoResponse) {
        stoRetrofit.deletePoint(stoId = selectedSTO.id)
    }
}