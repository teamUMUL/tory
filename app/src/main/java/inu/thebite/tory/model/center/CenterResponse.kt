package inu.thebite.tory.model.center

import com.google.gson.annotations.SerializedName

data class CenterResponse (
    
    @SerializedName("center_seq")
    var id: Long,

    @SerializedName("center_name")
    var name: String
)