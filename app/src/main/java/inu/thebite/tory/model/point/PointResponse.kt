package inu.thebite.tory.model.point

import com.google.gson.annotations.SerializedName

data class PointResponse(

    @SerializedName("point_seq")
    var id: Long,

    @SerializedName("point_round")
    var round: Int,

    @SerializedName("point_reg_mbr_seq")
    var registrant: String,

    @SerializedName("point_reg_dt")
    var registerDate: String,

    @SerializedName("sto_seq")
    var stoId: Long,

    @SerializedName("student_seq")
    var studentId: Long
)
