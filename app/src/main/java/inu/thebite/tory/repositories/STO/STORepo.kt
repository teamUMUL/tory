package inu.thebite.tory.repositories.STO

import inu.thebite.tory.model.image.UpdateImageListRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.point.AddPointRequest
import inu.thebite.tory.model.sto.AddStoRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.UpdateStoRequest
import inu.thebite.tory.model.sto.UpdateStoRoundRequest
import inu.thebite.tory.model.sto.UpdateStoStatusRequest
import retrofit2.Response

interface STORepo {

    suspend fun addSto(ltoInfo: LtoResponse, addStoRequest: AddStoRequest) : Response<StoResponse>

    suspend fun updateStoStatus(stoInfo: StoResponse, updateStoStatusRequest: UpdateStoStatusRequest) : Response<StoResponse>

    suspend fun updateStoHitStatus(stoInfo: StoResponse, updateStoStatusRequest: UpdateStoStatusRequest) : Response<StoResponse>

    suspend fun updateSto(stoInfo: StoResponse, updateStoRequest: UpdateStoRequest) : Response<StoResponse>

    suspend fun updateImageList(stoInfo: StoResponse, updateImageListRequest: UpdateImageListRequest) : Response<StoResponse>

    suspend fun getAllSTOs(studentId: Long) : List<StoResponse>

    suspend fun getSTOsByLTO(ltoId: Long): List<StoResponse>

    suspend fun deleteSto(stoInfo: StoResponse) : Response<Boolean>

    suspend fun getPointList(selectedSTO : StoResponse) : List<String>

    suspend fun addPoint(selectedSTO: StoResponse, addPointRequest: AddPointRequest)

    suspend fun addRound(selectedSTO: StoResponse, updateStoRoundRequest: UpdateStoRoundRequest) : Response<StoResponse>

    suspend fun addRoundHit(selectedSTO: StoResponse, updateStoRoundRequest: UpdateStoRoundRequest) : Response<StoResponse>

    suspend fun deletePoint(selectedSTO: StoResponse)

}