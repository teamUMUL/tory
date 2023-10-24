package inu.thebite.tory.repositories.image

import inu.thebite.tory.model.image.ImageResponse
import inu.thebite.tory.model.lto.LtoResponse

interface ImageRepo {

    suspend fun getAllImages(): List<ImageResponse>

}