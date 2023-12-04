package inu.thebite.tory.screens.notice

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.model.detail.DetailResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.notice.AddCommentRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.repositories.notice.NoticeRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoticeViewModel : ViewModel() {

    private val repo: NoticeRepoImpl = NoticeRepoImpl()

    private val _selectedNoticeDates: MutableStateFlow<List<NoticeDate>?> = MutableStateFlow(null)
    val selectedNoticeDates = _selectedNoticeDates.asStateFlow()

    private val _selectedNoticeDate: MutableStateFlow<NoticeDate?> = MutableStateFlow(null)
    val selectedNoticeDate = _selectedNoticeDate.asStateFlow()

    private val _selectedNoticeDetailList: MutableStateFlow<List<DetailResponse>?> = MutableStateFlow(null)
    val selectedNoticeDetailList = _selectedNoticeDetailList.asStateFlow()

    private val _selectedNoticeLTOs: MutableStateFlow<List<LtoResponse>?> = MutableStateFlow(null)
    val selectedNoticeLTOs = _selectedNoticeLTOs.asStateFlow()

//    fun setSelectedNoticeDates(
//        allDates: List<NoticeDate>,
//        selectedYear: String,
//        selectedMonth: String
//    ) {
//        viewModelScope.launch {
//            val result = allDates.filter {
//                it.year == selectedYear &&
//                it.month == selectedMonth
//            }.sortedByDescending { it.date.toInt() }
//
//            _selectedNoticeDates.update {
//                result
//            }
//        }
//    }
    init {
    }
    fun setSelectedNoticeDate(
        noticeDate: NoticeDate
    ){
        _selectedNoticeDate.update {
            noticeDate
        }
    }

    fun getNoticeDateList(
        studentId: Long,
        year: String,
        month: String
    ){
        viewModelScope.launch {
            try {
                val stringDates = repo.getNoticeDateList(
                    studentId = studentId,
                    year = year,
                    month = month
                )
                val noticeDates = parseDateStrings(stringDates)
                _selectedNoticeDates.update { noticeDates }
            } catch (e: Exception) {
                Log.e("failed to get NoticeDateList", e.message.toString())
            }
        }
    }

    fun getNotice(
        studentId: Long,
        date: String
    ){
        try {
            viewModelScope.launch {
                repo.getNotice(studentId = studentId, date = date)
            }
        } catch (e: Exception){
            Log.e("failed to get Notice", e.message.toString())
        }
    }

    fun updateTodayComment(
        studentId: Long,
        date: String,
        addCommentRequest: AddCommentRequest
    ){
        try {
            viewModelScope.launch {
                repo.updateTodayComment(studentId = studentId, date = date, addCommentRequest = addCommentRequest)
            }
        } catch (e: Exception){
            Log.e("failed to update TodayComment", e.message.toString())
        }
    }

    fun updateLTOComment(
        studentId: Long,
        date: String,
        stoId: Long,
        addCommentRequest: AddCommentRequest
    ){
        try {
            viewModelScope.launch {
                repo.updateLTOComment(studentId = studentId, date = date, stoId = stoId,addCommentRequest = addCommentRequest)
            }
        } catch (e: Exception){
            Log.e("failed to update LTOComment", e.message.toString())
        }
    }

    fun addDetail(
        studentId: Long,
        date: String,
        stoId: Long
    ){
        viewModelScope.launch {
            try {
                val response = repo.addDetail(studentId = studentId, date = date, stoId = stoId)

                if (response.isSuccessful) {
                    val newDetailResponse = response.body() ?: throw Exception("Detail 정보가 비어있습니다.")
                    _selectedNoticeDetailList.update { currentSTOs ->
                        currentSTOs?.let {
                            // 현재 STO 리스트가 null이 아니면 새 STO를 추가
                            it + newDetailResponse
                        } ?: listOf(newDetailResponse) // 현재 STO 리스트가 null이면 새 리스트를 생성
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("Detail 추가 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to add Detail", e.message.toString())
            }
        }
    }

    fun getDetailList(
        studentId: Long,
        date: String
    ){
        try {
            viewModelScope.launch {
                val response = repo.getDetailList(studentId = studentId, date = date)

                if (response.isSuccessful){
                    val updatedDetailList = response.body() ?: throw  Exception("DetailList 정보가 비어있습니다.")

                    _selectedNoticeDetailList.update {
                        updatedDetailList
                    }
                }
            }
        } catch (e: Exception){
            Log.e("failed to get DetailList", e.message.toString())
        }
    }
}

fun parseDateStrings(dateStrings: List<String>): List<NoticeDate> {
    return dateStrings.map { dateString ->
        val parts = dateString.split("/", " ")
        NoticeDate(
            year = parts[0],
            month = parts[1],
            date = parts[2],
            day = parts[3]
        )
    }
}