package inu.thebite.tory.model.image


import com.google.gson.annotations.SerializedName
import inu.thebite.tory.model.category.CategoryResponse

data class ImageResponse(

    @SerializedName("id")
    var id: Long,

    @SerializedName("name")
    var name: String,

    @SerializedName("url")
    var url: String,

    @SerializedName("category")
    var category: CategoryResponse
)