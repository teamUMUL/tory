package inu.thebite.tory.repositories.notice

import inu.thebite.tory.model.detail.DetailResponse
import inu.thebite.tory.model.notice.AddCommentRequest
import inu.thebite.tory.model.notice.NoticeResponse
import retrofit2.Response

interface NoticeRepo {

    suspend fun updateTodayComment(studentId: Long, year: String, month: String, date: String, addCommentRequest: AddCommentRequest)

    suspend fun getNoticeDateList(studentId: Long, year: String, month: String): List<String>

    suspend fun getNotice(studentId: Long, year: String, month: String, date: String) : Response<NoticeResponse>

    suspend fun addDetail(studentId: Long, year: String, month: String, date: String, stoId: Long) : Response<DetailResponse>

    suspend fun updateLTOComment(studentId: Long, year: String, month: String, date: String, stoId: Long, addCommentRequest: AddCommentRequest)

    suspend fun getDetailList(studentId: Long, year: String, month: String, date: String) : Response<List<DetailResponse>>

}
