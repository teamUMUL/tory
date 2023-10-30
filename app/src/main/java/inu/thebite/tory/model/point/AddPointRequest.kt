package inu.thebite.tory.model.point

import com.google.gson.annotations.SerializedName

data class AddPointRequest(

    @SerializedName("result")
    var result: String,

    @SerializedName("registrant")
    var registrant: String
)
