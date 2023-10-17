package inu.thebite.tory.model.lto

import com.google.gson.annotations.SerializedName

data class LtoResponse(

    @SerializedName("lto_seq")
    var id: String,

    @SerializedName("tmpl_seq")
    var templateNum: Int,

    @SerializedName("lto_status")
    var status: String,

    @SerializedName("lto_name")
    var name: String,

    @SerializedName("lto_contents")
    var contents: String,

    @SerializedName("game")
    var game: String,

    @SerializedName("lto_arr_dt")
    var achieveDate: String,

    @SerializedName("lto_reg_dt")
    var registerDate: String,

    @SerializedName("del_yn")
    var delYN: String,

    @SerializedName("domain_seq")
    var domainId: Int
)
