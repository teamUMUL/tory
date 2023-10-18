package inu.thebite.tory.model.category

import com.google.gson.annotations.SerializedName

data class CategoryResponse(

    @SerializedName("id")
    var id: Long,

    @SerializedName("name")
    var name: String
)
