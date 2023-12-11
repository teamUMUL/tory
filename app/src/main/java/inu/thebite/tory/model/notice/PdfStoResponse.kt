package inu.thebite.tory.model.notice

import com.google.gson.annotations.SerializedName

data class PdfStoResponse(

    @SerializedName("name")
    var name: String,

    @SerializedName("date")
    var date: List<String>,

    @SerializedName("plusRate")
    var plusRate: List<Float>,

    @SerializedName("minusRate")
    var minusRate: List<Float>
)
