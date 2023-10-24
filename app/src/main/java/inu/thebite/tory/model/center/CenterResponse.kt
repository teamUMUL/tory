package inu.thebite.tory.model.center

import com.google.gson.annotations.SerializedName

data class CenterResponse (
    
    @SerializedName("id")
    var id: Long,

    @SerializedName("name")
    var name: String
)