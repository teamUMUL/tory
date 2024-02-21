package inu.thebite.tory.model.member

import com.google.gson.annotations.SerializedName

data class AddMemberRequest (

    @SerializedName("id")
    var id: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("phone")
    var phone: String
)