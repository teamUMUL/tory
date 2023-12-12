package inu.thebite.tory.repositories.Center

import inu.thebite.tory.model.center.CenterRequest
import inu.thebite.tory.model.center.CenterResponse
import retrofit2.Response

interface CenterRepo {
    suspend fun createCenter(center: CenterRequest): Response<CenterResponse>
    suspend fun getAllCenters(): List<CenterResponse>
    suspend fun updateCenter(centerResponse: CenterResponse, center: CenterRequest): Response<CenterResponse>
    suspend fun deleteCenter(center: CenterResponse)
}