package inu.thebite.tory.model.todo

import com.google.gson.annotations.SerializedName
import inu.thebite.tory.model.student.StudentResponse

data class TodoResponse(

    @SerializedName("id")
    var id: Long,

    @SerializedName("date")
    var date: String,

    @SerializedName("stoList")
    var stoList: List<Long>,

    @SerializedName("student")
    var student: StudentResponse
)