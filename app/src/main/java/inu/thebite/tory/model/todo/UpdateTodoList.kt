package inu.thebite.tory.model.todo

import com.google.gson.annotations.SerializedName

data class UpdateTodoList(

    @SerializedName("stoList")
    var stoList: List<Long>
)