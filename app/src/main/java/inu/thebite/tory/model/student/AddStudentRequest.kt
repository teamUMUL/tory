package inu.thebite.tory.model.student

import com.google.gson.annotations.SerializedName

data class AddStudentRequest(

    @SerializedName("name")
    var name: String,

    @SerializedName("birth")
    var birth: String,

    @SerializedName("etc")
    var etc: String,

    @SerializedName("parentName")
    var parentName: String,

    @SerializedName("startDate")
    var startDate: String
)
