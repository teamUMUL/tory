package inu.thebite.tory.model.notice

import com.google.gson.annotations.SerializedName
import inu.thebite.tory.model.student.StudentResponse

data class NoticeResponse(

    @SerializedName("id")
    var id: Long,

    @SerializedName("date")
    var date: String,

    @SerializedName("comment")
    var comment: String,

    @SerializedName("student")
    var student: StudentResponse
)
