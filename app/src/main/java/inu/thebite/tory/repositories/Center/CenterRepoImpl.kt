package inu.thebite.tory.repositories.Center

import inu.thebite.tory.model.center.CenterRequest
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.retrofit.RetrofitApi
import inu.thebite.tory.retrofit.RetrofitService
import kotlinx.coroutines.flow.Flow

class CenterRepoImpl: CenterRepo {
    private val centerRetrofit = RetrofitApi.apiService

    override suspend fun createCenter(center: CenterRequest) {
        centerRetrofit.addCenter(center)
    }

    override suspend fun getAllCenters(): List<CenterResponse> {
        return centerRetrofit.getCenterList()
    }

    override suspend fun deleteCenter(center: CenterResponse) {
        centerRetrofit.deleteCenter(center.id)
    }
}