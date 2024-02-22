package inu.thebite.tory.model.member

import com.google.gson.annotations.SerializedName

data class FindMemberIdRequest(

    @SerializedName("name")
    var name: String,

    @SerializedName("phone")
    var phone: String,

    @SerializedName("email")
    var email: String
)
