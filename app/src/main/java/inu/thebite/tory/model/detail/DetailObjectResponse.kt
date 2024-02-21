package inu.thebite.tory.model.detail

import com.google.gson.annotations.SerializedName

data class DetailObjectResponse(

    @SerializedName("id")
    var id: Long,

    @SerializedName("comment")
    var comment: String,

    @SerializedName("detailGraphResponse")
    var detailGraphResponse: DetailGraphResponse
)
