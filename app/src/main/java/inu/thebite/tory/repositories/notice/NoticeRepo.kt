package inu.thebite.tory.repositories.notice

import inu.thebite.tory.model.detail.DetailResponse
import inu.thebite.tory.model.notice.AddCommentRequest
import inu.thebite.tory.model.notice.NoticeResponse
import retrofit2.Response

interface NoticeRepo {

    suspend fun updateTodayComment(studentId: Long, date: String, addCommentRequest: AddCommentRequest)

    suspend fun getNoticeDateList(studentId: Long, year: String, month: String): List<String>

    suspend fun getNotice(studentId: Long, date: String) : Response<NoticeResponse>

    suspend fun addDetail(studentId: Long, date: String, stoId: Long)

    suspend fun updateLTOComment(studentId: Long, date: String, stoId: Long, addCommentRequest: AddCommentRequest)

    suspend fun getDetailList(studentId: Long, date: String) : Response<DetailResponse>

}