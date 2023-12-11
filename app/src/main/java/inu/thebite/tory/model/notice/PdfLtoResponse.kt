package inu.thebite.tory.model.notice

import com.google.gson.annotations.SerializedName

data class PdfLtoResponse(

    @SerializedName("title")
    var title: String,

    @SerializedName("content")
    var content: String,

    @SerializedName("stoArray")
    var stoArray: List<PdfStoResponse>
)
