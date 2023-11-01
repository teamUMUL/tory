package inu.thebite.tory.model.point

import com.google.gson.annotations.SerializedName

data class DeletePointRequest(

    @SerializedName("registrant")
    var registrant : String
)
