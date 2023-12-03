package inu.thebite.tory.model.sto

import com.google.gson.annotations.SerializedName
import inu.thebite.tory.model.lto.LtoResponse

data class StoSummaryResponse(

    @SerializedName("id")
    var id: Long,

    @SerializedName("name")
    var name: String,

    @SerializedName("status")
    var status: String,

    @SerializedName("lto")
    var lto: LtoResponse
)
