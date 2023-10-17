package inu.thebite.tory.model.sto

import com.google.gson.annotations.SerializedName

data class StoResponse(

    @SerializedName("sto_seq")
    var id: Long,

    @SerializedName("tmpl_seq")
    var templateNum: Int,

    @SerializedName("sto_status")
    var status: String,

    @SerializedName("sto_name")
    var name: String,

    @SerializedName("sto_contents")
    var contents: String,

    @SerializedName("sto_trial_cnt")
    var count: Int,

    @SerializedName("sto_arr_std_cnt")
    var goal: Int,

    @SerializedName("sto_arr_std_pst")
    var goalPercent: Int,

    @SerializedName("sto_arr_yn")
    var achievementOrNot: String,

    @SerializedName("sto_urge_tp_cd")
    var urgeType: String,

    @SerializedName("sto_urge_contents")
    var urgeContent: String,

    @SerializedName("sto_enforce_contents")
    var enforceContent: String,

    @SerializedName("sto_memo_contents")
    var memo: String,

    @SerializedName("sto_arr_dt")
    var hitGoalDate: String,

    @SerializedName("sto_reg_dt")
    var registerDate: String,

    @SerializedName("del_yn")
    var delYN: String,

//    @SerializedName("sto_image_list")
//    var imageList: List<ImageResponse>,

    @SerializedName("name")
    var ltoId: Long,
)
