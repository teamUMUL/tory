package inu.thebite.tory.model.lto

import com.google.gson.annotations.SerializedName

data class LtoResponse(

    @SerializedName("ltoId")
    var id: Long,

    @SerializedName("templateNum")
    var templateNum: Int,

    @SerializedName("status")
    var status: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("contents")
    var contents: String,

    @SerializedName("game")
    var game: String,

    @SerializedName("developType")
    var developType: List<String>,

    @SerializedName("achieveDate")
    var achieveDate: String,

    @SerializedName("registerDate")
    var registerDate: String,

    @SerializedName("delYN")
    var delYN: String,

    @SerializedName("domainId")
    var domainId: Long,

    @SerializedName("studentId")
    var studentId: Long
)
