package inu.thebite.tory.model.detail

import com.google.gson.annotations.SerializedName
import inu.thebite.tory.model.notice.NoticeResponse

data class DetailResponse(

    @SerializedName("id")
    var id: Long,

    @SerializedName("comment")
    var comment: String,

    @SerializedName("stoId")
    var stoId: Set<Long>,

    @SerializedName("ltoId")
    var ltoId: Long,

    @SerializedName("notice")
    var notice: NoticeResponse
)
