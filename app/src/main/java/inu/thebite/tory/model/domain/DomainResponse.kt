package inu.thebite.tory.model.domain

import com.google.gson.annotations.SerializedName

data class DomainResponse(

    @SerializedName("id")
    var id: Long,

    @SerializedName("centerId")
    var centerId: Long,

    @SerializedName("templateNum")
    var templateNum: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("registerDate")
    var registerDate: String
)
