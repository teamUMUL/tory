package inu.thebite.tory.model.lto

import com.google.gson.annotations.SerializedName

data class UpdateLtoStatusRequest(

    @SerializedName("status")
    var status: String
)
