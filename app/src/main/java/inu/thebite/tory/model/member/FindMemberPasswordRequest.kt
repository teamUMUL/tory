package inu.thebite.tory.model.member

import com.google.gson.annotations.SerializedName

data class FindMemberPasswordRequest(

    @SerializedName("id")
    var id: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("phone")
    var phone: String
)
