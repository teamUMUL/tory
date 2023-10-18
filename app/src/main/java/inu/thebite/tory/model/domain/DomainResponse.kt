package inu.thebite.tory.model.domain

import com.google.gson.annotations.SerializedName

data class DomainResponse(

    @SerializedName("id")
    var id: Long,

    @SerializedName("templateNum")
    var templateNum: Int,

    @SerializedName("type")
    var type: String,

    @SerializedName("status")
    var status: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("contents")
    var contents: String,

    @SerializedName("useYN")
    var useYN: String,

    @SerializedName("delYN")
    var delYN: String,

    @SerializedName("registerDate")
    var registerDate: String
)
