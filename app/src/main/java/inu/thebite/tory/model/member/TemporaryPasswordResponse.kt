package inu.thebite.tory.model.member

import com.google.gson.annotations.SerializedName

data class TemporaryPasswordResponse(

    @SerializedName("password")
    var password: String
)
