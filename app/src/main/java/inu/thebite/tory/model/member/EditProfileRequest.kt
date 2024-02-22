package inu.thebite.tory.model.member

import com.google.gson.annotations.SerializedName

data class EditProfileRequest(

    @SerializedName("forte")
    var forte: String,

    @SerializedName("qualification")
    var qualification: List<String>
)
