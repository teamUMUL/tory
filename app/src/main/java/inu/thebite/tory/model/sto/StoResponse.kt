package inu.thebite.tory.model.sto

import com.google.gson.annotations.SerializedName
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.image.ImageResponse


data class StoResponse(

    @SerializedName("id")
    var id: Long,

    @SerializedName("templateNum")
    var templateNum: Int,

    @SerializedName("status")
    var status: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("contents")
    var contents: String,

    @SerializedName("count")
    var count: Int,

    @SerializedName("goal")
    var goal: Int,

    @SerializedName("goalPercent")
    var goalPercent: Int,

    @SerializedName("achievementOrNot")
    var achievementOrNot: String,

    @SerializedName("urgeType")
    var urgeType: String,

    @SerializedName("urgeContent")
    var urgeContent: String,

    @SerializedName("enforceContent")
    var enforceContent: String,

    @SerializedName("memo")
    var memo: String,

    @SerializedName("hitGoalDate")
    var hitGoalDate: String,

    @SerializedName("registerDate")
    var registerDate: String,

    @SerializedName("delYN")
    var delYN: String,

    @SerializedName("imageList")
    var imageList: List<ImageResponse>,

    @SerializedName("lto")
    var lto: LtoResponse

)
