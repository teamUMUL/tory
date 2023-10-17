package inu.thebite.tory.model.sto

import com.google.gson.annotations.SerializedName

data class AddStoRequest(

    @SerializedName("sto_name")
    var name: String,

    @SerializedName("sto_contents")
    var contents: String,

    @SerializedName("sto_count")
    var count: Int,

    @SerializedName("sto_goal")
    var goal: Int,

    @SerializedName("sto_urgeType")
    var urgeType: String,

    @SerializedName("sto_urgeContent")
    var urgeContent: String,

    @SerializedName("sto_enforceContent")
    var enforceContent: String,

    @SerializedName("sto_memo")
    var memo: String,
)
