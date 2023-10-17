package inu.thebite.tory.model.student

import com.google.gson.annotations.SerializedName

data class StudentResponse(

    @SerializedName("student_seq")
    var id: Long,

    @SerializedName("student_name")
    var name: String,

    @SerializedName("student_birth")
    var birth: String,

    @SerializedName("student_etc")
    var etc: String,

    @SerializedName("parent_name")
    var parentName: String,

    @SerializedName("student_start_dt")
    var startDate: String,

    @SerializedName("student_end_dt")
    var endDate: String,

    @SerializedName("student_reg_dt")
    var registerDate: String,

    @SerializedName("class_seq")
    var toryClass: String
)
