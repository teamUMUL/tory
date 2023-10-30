package inu.thebite.tory.model.point

import com.google.gson.annotations.SerializedName

data class UpdatePointRequest(

    @SerializedName("points")
    var points: List<String>,

    @SerializedName("registrant")
    var registrant: String
)
