package inu.thebite.tory.model.category

import com.google.gson.annotations.SerializedName

data class AddCategoryRequest(

    @SerializedName("name")
    var name: String
)
