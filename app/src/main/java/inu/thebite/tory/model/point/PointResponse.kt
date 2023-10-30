package inu.thebite.tory.model.point

import com.google.gson.annotations.SerializedName
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.student.StudentResponse

data class PointResponse(

    @SerializedName("id")
    var id: Long,

    @SerializedName("round")
    var round: Int,

    @SerializedName("points")
    var points: List<String>,

    @SerializedName("registrant")
    var registrant: String,

    @SerializedName("registerDate")
    var registerDate: String,

    @SerializedName("sto")
    var sto: StoResponse,

    @SerializedName("student")
    var student: StudentResponse
)
