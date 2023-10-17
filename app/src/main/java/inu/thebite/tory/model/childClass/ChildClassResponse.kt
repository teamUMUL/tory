package inu.thebite.tory.model.childClass

import com.google.gson.annotations.SerializedName

data class ChildClassResponse (

    @SerializedName("class_seq")
    var id: Long,

    @SerializedName("class_name")
    var name: String,

    @SerializedName("center_seq")
    var centerId: Long
)