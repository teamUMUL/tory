package inu.thebite.tory.model.student

import com.google.gson.annotations.SerializedName

data class UpdateStudentRequest(

    @SerializedName("name")
    var name: String,

    @SerializedName("birth")
    var birth: String,

    @SerializedName("etc")
    var etc: String,

    @SerializedName("parentName")
    var parentName: String,

    @SerializedName("startDate")
    var startDate: String,

    @SerializedName("endDate")
    var endDate: String,

    @SerializedName("registerDate")
    var registerDate: String

)
