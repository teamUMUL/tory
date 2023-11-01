package inu.thebite.tory.model.sto

import com.google.gson.annotations.SerializedName

data class UpdateStoRoundRequest(

    @SerializedName("registrant")
    var registrant : String
)
