package inu.thebite.tory.model.domain

import com.google.gson.annotations.SerializedName

data class AddDomainRequest(

    @SerializedName("name")
    var name: String,

    @SerializedName("type")
    var type: String,

    @SerializedName("contents")
    var contents: String
)
