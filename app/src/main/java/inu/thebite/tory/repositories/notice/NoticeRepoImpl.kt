package inu.thebite.tory.repositories.notice

import inu.thebite.tory.model.detail.DetailGraphResponse
import inu.thebite.tory.model.detail.DetailResponse
import inu.thebite.tory.model.notice.AddCommentRequest
import inu.thebite.tory.model.notice.ConvertPdfRequest
import inu.thebite.tory.model.notice.DateResponse
import inu.thebite.tory.model.notice.NoticeDatesResponse
import inu.thebite.tory.model.notice.NoticeResponse
import inu.thebite.tory.retrofit.RetrofitApi
import inu.thebite.tory.screens.notice.NoticeDate
import retrofit2.Response

class NoticeRepoImpl : NoticeRepo {

    private val noticeRetrofit = RetrofitApi.apiService

    override suspend fun updateTodayComment(studentId: Long, year: String, month: String, date: String, addCommentRequest: AddCommentRequest) : Response<NoticeResponse>{
        return noticeRetrofit.updateComment(studentId = studentId, year = year, month= month.toInt(), date = date, addCommentRequest = addCommentRequest)
    }

    override suspend fun getNoticeYearsAndMonths(studentId: Long): Response<List<NoticeDatesResponse>> {
        return noticeRetrofit.getNoticeDates(studentId = studentId)
    }

    override suspend fun updateLTOComment(studentId: Long, year: String, month: String, date: String, stoId: Long, addCommentRequest: AddCommentRequest) : Response<DetailResponse> {
        return noticeRetrofit.updateComment(studentId = studentId, year = year, month = month.toInt(), date = date, stoId = stoId, addCommentRequest = addCommentRequest)
    }

    override suspend fun getNoticeDateList(studentId: Long, year: String, month: String): List<DateResponse> {
        return noticeRetrofit.getNoticeDateList(studentId = studentId, year = year, month = month.toInt())
    }

    override suspend fun getNotice(studentId: Long, year: String, month: String, date: String): Response<NoticeResponse> {
        return noticeRetrofit.getNotice(studentId = studentId, year = year, month = month.toInt(), date = date)
    }

    override suspend fun addDetail(studentId: Long, year: String, month: String, date: String, stoId: Long) : Response<DetailResponse> {
        return noticeRetrofit.addDetail(studentId = studentId, year = year, month = month.toInt(), date = date, stoId = stoId)
    }

    override suspend fun getDetailList(studentId: Long, year:String, month:String, date: String): Response<List<DetailGraphResponse>> {
        return noticeRetrofit.getDetailList(studentId = studentId, year = year, month = month.toInt(), date = date)
    }

    override suspend fun createSharePdf(studentId: Long, year: String, month: Int, date: String, convertPdfRequest: ConvertPdfRequest): Response<String> {
        return noticeRetrofit.createSharePdf(studentId = studentId, year = year, month = month, date = date, convertPdfRequest = convertPdfRequest)
    }
}
