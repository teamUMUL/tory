package inu.thebite.tory.model.student

import com.google.gson.annotations.SerializedName

data class UpdateStudentRequest(

    @SerializedName("date")
    var date: String
)
