package inu.thebite.tory.model.student

import com.google.gson.annotations.SerializedName
import inu.thebite.tory.model.childClass.ChildClassResponse

data class StudentResponse(

    @SerializedName("id")
    var id: Long,

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
    var registerDate: String,

    @SerializedName("toryClass")
    var childClass: ChildClassResponse
)
