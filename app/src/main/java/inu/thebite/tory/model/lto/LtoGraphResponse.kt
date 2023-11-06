package inu.thebite.tory.model.lto

import com.google.gson.annotations.SerializedName

data class LtoGraphResponse(

    @SerializedName("stoId")
    var stoId: Long,

    @SerializedName("result")
    var result: List<Float>,

    @SerializedName("date")
    var date: List<String>
)
