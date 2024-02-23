package inu.thebite.tory.model.lto

import com.google.gson.annotations.SerializedName

data class DevelopTypeResponse(

    @SerializedName("developType")
    var developType: List<String>
)
