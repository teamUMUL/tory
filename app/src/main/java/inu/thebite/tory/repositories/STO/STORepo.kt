package inu.thebite.tory.repositories.STO

import inu.thebite.tory.model.image.UpdateImageListRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.AddStoRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.UpdateStoRequest
import inu.thebite.tory.model.sto.UpdateStoStatusRequest
import kotlinx.coroutines.flow.Flow

interface STORepo {

    suspend fun addSto(ltoInfo: LtoResponse, addStoRequest: AddStoRequest)

    suspend fun updateStoStatus(stoInfo: StoResponse, updateStoStatusRequest: UpdateStoStatusRequest)

    suspend fun updateStoHitStatus(stoInfo: StoResponse, updateStoStatusRequest: UpdateStoStatusRequest)

    suspend fun updateSto(stoInfo: StoResponse, updateStoRequest: UpdateStoRequest)

    suspend fun updateImageList(stoInfo: StoResponse, updateImageListRequest: UpdateImageListRequest)

    suspend fun getStoList(): List<StoResponse>

    suspend fun deleteSto(stoInfo: StoResponse)

    suspend fun getPointList(selectedSTO : StoResponse) : List<String>
}