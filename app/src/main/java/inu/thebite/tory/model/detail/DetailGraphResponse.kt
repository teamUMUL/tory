package inu.thebite.tory.model.detail

import com.google.gson.annotations.SerializedName

data class DetailGraphResponse(

    @SerializedName("id")
    var id: Long,

    @SerializedName("comment")
    var comment: String,

    @SerializedName("results")
    var results: List<Float>,

    @SerializedName("dates")
    var dates: List<String>,

    @SerializedName("stoId")
    var stoId: Long,

    @SerializedName("noticeId")
    var noticeId: Long
)