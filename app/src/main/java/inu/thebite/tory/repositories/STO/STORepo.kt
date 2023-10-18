package inu.thebite.tory.repositories.STO

import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.AddStoRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.UpdateStoStatusRequest
import kotlinx.coroutines.flow.Flow

interface STORepo {

    suspend fun addSto(ltoInfo: LtoResponse, addStoRequest: AddStoRequest)

    suspend fun updateStoStatus(stoInfo: StoResponse, updateStoStatusRequest: UpdateStoStatusRequest)

    suspend fun updateStoHitStatus(stoInfo: StoResponse, updateStoStatusRequest: UpdateStoStatusRequest)

    suspend fun getStoList(): List<StoResponse>

    suspend fun deleteSto(stoInfo: StoResponse)
}