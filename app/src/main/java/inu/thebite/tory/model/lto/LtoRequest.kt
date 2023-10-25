package inu.thebite.tory.model.lto

import com.google.gson.annotations.SerializedName

data class LtoRequest(

    @SerializedName("name")
    var name: String,

    @SerializedName("contents")
    var contents: String,

    @SerializedName("game")
    var game: String
)
