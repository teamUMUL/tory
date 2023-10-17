package inu.thebite.tory.model.domain

import com.google.gson.annotations.SerializedName

data class AddDomainRequest(

    @SerializedName("domain_name")
    var name: String,

    @SerializedName("domain_type")
    var type: String,

    @SerializedName("domain_content")
    var contents: String
)
