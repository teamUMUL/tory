package inu.thebite.tory.model.member

import com.google.gson.annotations.SerializedName

data class MemberLoginRequest(

    @SerializedName("id")
    var id: String,

    @SerializedName("password")
    var password: String
)
