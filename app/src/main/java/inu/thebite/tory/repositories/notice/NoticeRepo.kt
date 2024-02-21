package inu.thebite.tory.repositories.notice

import inu.thebite.tory.model.detail.DetailGraphResponse
import inu.thebite.tory.model.detail.DetailObjectResponse
import inu.thebite.tory.model.detail.DetailResponse
import inu.thebite.tory.model.notice.AddCommentRequest
import inu.thebite.tory.model.notice.AutoCommentResponse
import inu.thebite.tory.model.notice.ConvertPdfRequest
import inu.thebite.tory.model.notice.DateResponse
import inu.thebite.tory.model.notice.NoticeDatesResponse
import inu.thebite.tory.model.notice.NoticeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NoticeRepo {

    suspend fun updateTodayComment(studentId: Long, year: String, month: String, date: String, addCommentRequest: AddCommentRequest) : Response<NoticeResponse>

    suspend fun getNoticeYearsAndMonths(studentId: Long) : Response<List<NoticeDatesResponse>>

    suspend fun getNoticeDateList(studentId: Long, year: String, month: String): List<DateResponse>

    suspend fun getNotice(studentId: Long, year: String, month: String, date: String) : Response<NoticeResponse>

    suspend fun addDetail(studentId: Long, year: String, month: String, date: String, stoId: Long) : Response<DetailResponse>

    suspend fun updateLTOComment(studentId: Long, year: String, month: String, date: String, stoId: Long, addCommentRequest: AddCommentRequest) : Response<DetailResponse>

    suspend fun getDetailList(studentId: Long, year: String, month: String, date: String) : Response<List<DetailObjectResponse>>

    suspend fun createSharePdf(studentId: Long, year: String, month: Int, date: String)

    suspend fun getDetailAutoComment(studentId: Long, ltoId: Long, year: String, month: Int, date: String) : Response<AutoCommentResponse>

    suspend fun getNoticeAutoComment(studentId: Long, year: String, month: Int, date: String) : Response<AutoCommentResponse>
}
