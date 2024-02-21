package inu.thebite.tory.model.detail

import com.google.gson.annotations.SerializedName

data class DetailGraphResponse(

    @SerializedName("results")
    var results: List<Float>,

    @SerializedName("dates")
    var dates: List<String>,

    @SerializedName("stoId")
    var stoId: Long,

    @SerializedName("ltoId")
    var ltoId: Long,

    @SerializedName("noticeId")
    var noticeId: Long
)
