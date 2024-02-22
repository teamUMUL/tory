package inu.thebite.tory.model.member

import com.google.gson.annotations.SerializedName

data class UpdatePasswordRequest(

    @SerializedName("beforePassword")
    var beforePassword: String,

    @SerializedName("afterPassword")
    var afterPassword: String
)
