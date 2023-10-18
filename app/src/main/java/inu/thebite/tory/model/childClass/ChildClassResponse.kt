package inu.thebite.tory.model.childClass

import com.google.gson.annotations.SerializedName
import inu.thebite.tory.model.center.CenterResponse

data class ChildClassResponse (

    @SerializedName("id")
    var id: Long,

    @SerializedName("name")
    var name: String,

    @SerializedName("center")
    var center: CenterResponse
)