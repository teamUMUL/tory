package inu.thebite.tory.model.point

import com.google.gson.annotations.SerializedName

data class AddPointRequest(

    @SerializedName("points")
    var points: List<String>,

    @SerializedName("round")
    var round: Int,

    @SerializedName("registrant")
    var registrant: String
)
