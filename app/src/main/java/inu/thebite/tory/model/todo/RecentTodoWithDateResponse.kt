package inu.thebite.tory.model.todo

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class RecentTodoWithDateResponse(

    @SerializedName("date")
    var date: String,

    @SerializedName("sto")
    var sto: List<String>,

    @SerializedName("stoStatus")
    var stoStatus: List<String>,

    @SerializedName("lto")
    var lto: List<String>,

    @SerializedName("teacher")
    var teacher: String
)
