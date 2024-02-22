package inu.thebite.tory.model.member

import com.google.gson.annotations.SerializedName

data class MemberResponse(

    @SerializedName("name")
    var name: String,

    @SerializedName("forte")
    var forte: String,

    @SerializedName("qualification")
    var qualification: List<String>,

    @SerializedName("centerName")
    var centerName: String
)
