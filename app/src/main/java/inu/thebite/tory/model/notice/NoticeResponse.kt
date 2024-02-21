package inu.thebite.tory.model.notice

import com.google.gson.annotations.SerializedName
import inu.thebite.tory.model.student.StudentResponse

data class NoticeResponse(

    @SerializedName("id")
    var id: Long,

    @SerializedName("year")
    var year: String,

    @SerializedName("month")
    var month: Int,

    @SerializedName("date")
    var date: String,

    @SerializedName("day")
    var day: String,

    @SerializedName("comment")
    var comment: String,

    @SerializedName("studentId")
    var studentId: Long
)
