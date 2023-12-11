package inu.thebite.tory.model.notice

import com.google.gson.annotations.SerializedName

data class ConvertPdfRequest(

    @SerializedName("date")
    var date: String,

    @SerializedName("content")
    var content: String,

    @SerializedName("lto")
    var lto: List<PdfLtoResponse>
)
