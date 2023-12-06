package inu.thebite.tory.model.notice

import com.google.gson.annotations.SerializedName

data class NoticeDatesResponse(

    @SerializedName("year")
    var year: String,

    @SerializedName("month")
    var month: String
)
