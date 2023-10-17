package inu.thebite.tory.model.student

import com.google.gson.annotations.SerializedName

data class AddStudentRequest(

    @SerializedName("student_name")
    var name: String,

    @SerializedName("student_birth")
    var birth: String,

    @SerializedName("student_etc")
    var etc: String,

    @SerializedName("parentName")
    var parentName: String,

    @SerializedName("startDate")
    var startDate: String
)
