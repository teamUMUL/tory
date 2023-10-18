package inu.thebite.tory.model.student

import com.google.gson.annotations.SerializedName

data class UpdateStudentDateRequest(

    @SerializedName("date")
    var date: String
)
