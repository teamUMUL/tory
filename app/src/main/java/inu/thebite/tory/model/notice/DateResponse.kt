package inu.thebite.tory.model.notice

import com.google.gson.annotations.SerializedName

data class DateResponse(

    @SerializedName("year")
    var year: String,

    @SerializedName("month")
    var month: Int,

    @SerializedName("date")
    var date: String,

    @SerializedName("day")
    var day: String
)
