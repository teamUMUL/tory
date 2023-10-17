package inu.thebite.tory.model.lto

import com.google.gson.annotations.SerializedName

data class UpdateLtoStatusRequest(

    @SerializedName("lto_status")
    var status: String
)
