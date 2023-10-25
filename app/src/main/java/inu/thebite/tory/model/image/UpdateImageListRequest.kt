package inu.thebite.tory.model.image

import com.google.gson.annotations.SerializedName
import inu.thebite.tory.model.category.CategoryResponse

data class UpdateImageListRequest(

    @SerializedName("image")
    var image: List<ImageResponse>
)
