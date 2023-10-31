package inu.thebite.tory.model.image

import com.google.gson.annotations.SerializedName

data class UpdateImageListRequest(

    @SerializedName("imageList")
    var imageList: List<String>
)
