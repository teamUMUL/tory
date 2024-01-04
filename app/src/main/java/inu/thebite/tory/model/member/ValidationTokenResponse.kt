package inu.thebite.tory.model.member

import com.google.gson.annotations.SerializedName

data class ValidationTokenResponse(

    @SerializedName("name")
    var name: String,

    @SerializedName("result")
    var result: Boolean
)
