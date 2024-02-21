package inu.thebite.tory.model.sto

import com.google.gson.annotations.SerializedName

data class UpdateStoRequest(

    @SerializedName("name")
    var name: String,

    @SerializedName("contents")
    var contents: String,

    @SerializedName("count")
    var count: Int,

    @SerializedName("goal")
    var goal: Int,

    @SerializedName("goalType")
    var goalType: String,

    @SerializedName("goalAmount")
    var goalAmount: Int,

    @SerializedName("urgeContent")
    var urgeContent: String,

    @SerializedName("enforceContent")
    var enforceContent: String,

    @SerializedName("memo")
    var memo: String
)
