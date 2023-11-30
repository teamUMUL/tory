package inu.thebite.tory.screens.notice

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _selectedNoticeSTOs: MutableStateFlow<List<StoResponse>?> = MutableStateFlow(null)
    val selectedNoticeSTOs = _selectedNoticeSTOs.asStateFlow()

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



    fun getNoticeDateList(
        studentId: Long,
        year: String,
        month: String
    ){
        viewModelScope.launch {
//            try {
//                _selectedNoticeDates.update {
//                    val rawDate = repo.getNoticeDateList(
//                        studentId = studentId,
//                        year = year,
//                        month = month
//                    )
//                    NoticeDate(
////                        year = rawDate[],
//                    )
//                }
//            } catch (e: Exception) {
//                Log.e("failed to get NoticeDateList", e.message.toString())
//            }
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
        try {
            viewModelScope.launch {
                repo.addDetail(studentId = studentId, date = date, stoId = stoId)
            }
        } catch (e: Exception){
            Log.e("failed to add Detail", e.message.toString())
        }
    }

    fun getDetailList(
        studentId: Long,
        date: String
    ){
        try {
            viewModelScope.launch {
//                _selectedNoticeSTOs.update {
//                    repo.getDetailList(studentId = studentId, date = date)
//                }
            }
        } catch (e: Exception){
            Log.e("failed to get DetailList", e.message.toString())
        }
    }


}