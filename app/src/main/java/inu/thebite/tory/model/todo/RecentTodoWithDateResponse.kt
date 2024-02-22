package inu.thebite.tory.model.todo

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class RecentTodoWithDateResponse(

    @SerializedName("date")
    var date: LocalDate,

    @SerializedName("sto")
    var sto: List<String>,

    @SerializedName("lto")
    var lto: List<String>,

    @SerializedName("teacher")
    var teacher: String
)
