package inu.thebite.tory.model.domain

import com.google.gson.annotations.SerializedName

data class AddDomainRequest(

    @SerializedName("name")
    var name: String

)
