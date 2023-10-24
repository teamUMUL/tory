package inu.thebite.tory.repositories.image

import inu.thebite.tory.model.image.ImageResponse
import inu.thebite.tory.retrofit.RetrofitApi

class ImageRepoImpl : ImageRepo {

    private val imageRetrofit = RetrofitApi.apiService

    override suspend fun getAllImages(): List<ImageResponse> {
        return imageRetrofit.getImageList()
    }
}