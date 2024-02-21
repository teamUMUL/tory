package inu.thebite.tory.model.sto

import com.google.gson.annotations.SerializedName
import inu.thebite.tory.model.point.PointResponse


data class StoResponse(

    @SerializedName("stoId")
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

    @SerializedName("goalType")
    var goalType: String,

    @SerializedName("goalAmount")
    var goalAmount: Int,

    @SerializedName("achievementOrNot")
    var achievementOrNot: String,

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

    @SerializedName("round")
    var round: Int,

    @SerializedName("imageList")
    var imageList: List<String>,

    @SerializedName("pointList")
    var pointList: List<PointResponse>,

    @SerializedName("stressStatus")
    var stressStatus: String,

    @SerializedName("concentration")
    var concentration: String,

    @SerializedName("significant")
    var significant: String,

    @SerializedName("looseCannonList")
    var looseCannonList: List<String>,

    @SerializedName("ltoId")
    var ltoId: Long

)
