package inu.thebite.tory.model.member

import com.google.gson.annotations.SerializedName

data class EditProfileRequest(

    @SerializedName("name")
    var name: String,

    @SerializedName("centerCode")
    var centerCode: Long,

    @SerializedName("forte")
    var forte: String,

    @SerializedName("qualification")
    var qualification: List<String>
)
