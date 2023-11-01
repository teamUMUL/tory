package inu.thebite.tory.model.sto

import com.google.gson.annotations.SerializedName

data class UpdateStoRoundRequest(

    @SerializedName("plusRate")
    var plusRate : Float,

    @SerializedName("minusRate")
    var minusRate : Float,

    @SerializedName("status")
    var status : String,

    @SerializedName("registrant")
    var registrant : String
)
