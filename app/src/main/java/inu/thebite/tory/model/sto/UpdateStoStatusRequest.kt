package inu.thebite.tory.model.sto

import com.google.gson.annotations.SerializedName

data class UpdateStoStatusRequest(

    @SerializedName("status")
    var status: String
)
