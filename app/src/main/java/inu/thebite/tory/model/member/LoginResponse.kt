package inu.thebite.tory.model.member

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("name")
    var name: String,

    @SerializedName("token")
    var token: String
)
