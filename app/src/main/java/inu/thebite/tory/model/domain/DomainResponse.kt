package inu.thebite.tory.model.domain

import com.google.gson.annotations.SerializedName

data class DomainResponse(

    @SerializedName("domain_seq")
    var id: String,

    @SerializedName("tmpl_seq")
    var templateNum: Int,

    @SerializedName("domain_tp_cd")
    var type: String,

    @SerializedName("domain_status")
    var status: String,

    @SerializedName("domain_name")
    var name: String,

    @SerializedName("domain_content")
    var contents: String,

    @SerializedName("domain_use_yn")
    var useYN: String,

    @SerializedName("del_yn")
    var deleteYN: String,

    @SerializedName("domain_reg_Dt")
    var registerDate: String
)
