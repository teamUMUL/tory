package inu.thebite.tory.model.lto

import com.google.gson.annotations.SerializedName

data class AddLtoRequest(

    @SerializedName("lto_name")
    var name: String,

    @SerializedName("lto_content")
    var contents: String,

    @SerializedName("game")
    var game: String
)
