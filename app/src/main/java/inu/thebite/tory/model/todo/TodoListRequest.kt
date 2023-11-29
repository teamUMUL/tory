package inu.thebite.tory.model.todo

import com.google.gson.annotations.SerializedName

data class TodoListRequest(

    @SerializedName("stoId")
    var stoId: Long
)
