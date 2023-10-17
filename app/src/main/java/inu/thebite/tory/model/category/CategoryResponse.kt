package inu.thebite.tory.model.category

import com.google.gson.annotations.SerializedName

data class CategoryResponse(

    @SerializedName("category_seq")
    var id: Long,

    @SerializedName("category_name")
    var name: String
)
