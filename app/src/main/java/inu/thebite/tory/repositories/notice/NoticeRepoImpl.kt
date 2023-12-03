package inu.thebite.tory.repositories.notice

import inu.thebite.tory.model.detail.DetailResponse
import inu.thebite.tory.model.notice.AddCommentRequest
import inu.thebite.tory.model.notice.NoticeResponse
import inu.thebite.tory.retrofit.RetrofitApi
import retrofit2.Response

class NoticeRepoImpl : NoticeRepo {

    private val noticeRetrofit = RetrofitApi.apiService

    override suspend fun updateTodayComment(studentId: Long, date: String, addCommentRequest: AddCommentRequest) {
        noticeRetrofit.updateComment(studentId = studentId, date = date, addCommentRequest = addCommentRequest)
    }

    override suspend fun updateLTOComment(studentId: Long, date: String, stoId: Long, addCommentRequest: AddCommentRequest) {
        noticeRetrofit.updateComment(studentId = studentId, date = date, stoId = stoId, addCommentRequest = addCommentRequest)
    }

    override suspend fun getNoticeDateList(studentId: Long, year: String, month: String): List<String> {
        return noticeRetrofit.getNoticeDateList(studentId = studentId, year = year, month = month)
    }

    override suspend fun getNotice(studentId: Long, date: String): Response<NoticeResponse> {
        return noticeRetrofit.getNotice(studentId = studentId, date = date)
    }

    override suspend fun addDetail(studentId: Long, date: String, stoId: Long) {
        noticeRetrofit.addDetail(studentId = studentId, date = date, stoId = stoId)
    }

    override suspend fun getDetailList(studentId: Long, date: String): Response<List<DetailResponse>> {
        return noticeRetrofit.getDetailList(studentId = studentId, date = date)
    }
}
