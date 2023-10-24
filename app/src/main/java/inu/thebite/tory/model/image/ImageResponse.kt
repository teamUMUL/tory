package inu.thebite.tory.model.image


import com.google.gson.annotations.SerializedName

data class ImageResponse(

    @SerializedName("image_id")
    var id: Long,

    @SerializedName("image_name")
    var name: String,

    @SerializedName("image_url")
    var url: String,

    @SerializedName("category_seq")
    var categoryId: Long
)