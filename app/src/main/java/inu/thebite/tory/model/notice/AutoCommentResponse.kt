package inu.thebite.tory.model.notice

import com.google.gson.annotations.SerializedName

data class AutoCommentResponse(

    @SerializedName("comment")
    var comment: String
)
