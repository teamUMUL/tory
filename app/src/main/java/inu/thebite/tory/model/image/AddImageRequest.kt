package inu.thebite.tory.model.image

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class AddImageRequest(

    @SerializedName("image")
    var image: MultipartBody.Part
)
