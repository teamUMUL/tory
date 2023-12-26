package inu.thebite.tory.screens.notice

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.model.detail.DetailGraphResponse
import inu.thebite.tory.model.detail.DetailResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.notice.AddCommentRequest
import inu.thebite.tory.model.notice.ConvertPdfRequest
import inu.thebite.tory.model.notice.DateResponse
import inu.thebite.tory.model.notice.NoticeDatesResponse
import inu.thebite.tory.model.notice.NoticeResponse
import inu.thebite.tory.model.notice.PdfLtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.repositories.notice.NoticeRepoImpl
import inu.thebite.tory.screens.education.compose.sto.getCurrentMonth
import inu.thebite.tory.screens.education.compose.sto.getCurrentYear
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoticeViewModel : ViewModel() {

    private val repo: NoticeRepoImpl = NoticeRepoImpl()

    private val _selectedNoticeDates: MutableStateFlow<List<DateResponse>?> = MutableStateFlow(null)
    val selectedNoticeDates = _selectedNoticeDates.asStateFlow()

    private val _selectedNoticeDate: MutableStateFlow<DateResponse?> = MutableStateFlow(null)
    val selectedNoticeDate = _selectedNoticeDate.asStateFlow()

    private val _selectedNotice: MutableStateFlow<NoticeResponse?> = MutableStateFlow(null)
    val selectedNotice = _selectedNotice.asStateFlow()

    private val _noticeYearAndMonthList: MutableStateFlow<List<NoticeDatesResponse>?> = MutableStateFlow(null)
    val noticeYearAndMonthList = _noticeYearAndMonthList.asStateFlow()

    private val _noticeYearList: MutableStateFlow<List<String>?> = MutableStateFlow(null)
    val noticeYearList = _noticeYearList.asStateFlow()

    private val _noticeMonthList: MutableStateFlow<List<String>?> = MutableStateFlow(null)
    val noticeMonthList = _noticeMonthList.asStateFlow()

    private val _selectedNoticeDetailList: MutableStateFlow<List<DetailGraphResponse>?> = MutableStateFlow(null)
    val selectedNoticeDetailList = _selectedNoticeDetailList.asStateFlow()

    private val _selectedYear: MutableStateFlow<String?> = MutableStateFlow(null)
    val selectedYear = _selectedYear.asStateFlow()

    private val _selectedMonth: MutableStateFlow<String?> = MutableStateFlow(null)
    val selectedMonth = _selectedMonth.asStateFlow()

    private val _pdfUrl: MutableStateFlow<String?> = MutableStateFlow(null)
    val pdfUrl = _pdfUrl.asStateFlow()

    fun setSelectedYear(
        selectedYear: String
    ){
        _selectedYear.update {
            selectedYear
        }
    }

    fun clearAll(){
        _selectedNoticeDetailList.update { null }
        _selectedNotice.update { null }
        _selectedMonth.update { null }
        _selectedYear.update { null }
        _selectedNoticeDate.update { null }
        _selectedNoticeDates.update { null }
        _noticeMonthList.update { null }
//        _noticeYearList.update { null }
    }

    fun setSelectedMonth(
        selectedMonth: String
    ){
        _selectedMonth.update {
            selectedMonth
        }
    }

    fun clearSelectedMonth(
    ){
        _selectedMonth.update {
            null
        }
    }

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

    fun getNoticeYearsAndMonths(
        studentId: Long
    ){
        viewModelScope.launch {
            try {
                val response = repo.getNoticeYearsAndMonths(studentId = studentId)

                if (response.isSuccessful) {
                    val gotNoticeYearAndMonthList = response.body() ?: throw Exception("Notice Year, Month 정보가 비어있습니다.")
                    Log.d("gotNoticeYearAndMonthList", gotNoticeYearAndMonthList.toString())
                    _noticeYearAndMonthList.update {
                        gotNoticeYearAndMonthList
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("Notice 연월 데이터 가져오기 실패: $errorBody")
                }


            } catch (e: Exception) {
                Log.e("failed to get Notice Year And Month List", e.message.toString())
            }
        }
    }

    fun setNoticeYearList(){
       _noticeYearList.update {
           _noticeYearAndMonthList.value?.map { it.year } ?: emptyList()
       }
//        _selectedYear.update {
//            noticeYearList.value?.let { noticeYearList ->
//                val foundNoticeYear = noticeYearList.find { it == getCurrentYear() }
//                foundNoticeYear
//            }
//        }
    }

    fun setNoticeMonthList(
        selectedYear: String
    ){
        _noticeMonthList.update {
            val monthsByYear = _noticeYearAndMonthList.value?.filter { it.year == selectedYear } ?: emptyList()
            monthsByYear.map { it.month.toString() }
        }
//        _selectedMonth.update {
//            noticeMonthList.value?.let { noticeMonthList ->
//                val foundNoticeMonth = noticeMonthList.find { it == getCurrentMonth() }
//                foundNoticeMonth
//            }
//        }
    }

    fun setSelectedNoticeDate(
        noticeDate: DateResponse
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
                _selectedNoticeDates.update {
                    repo.getNoticeDateList(
                        studentId = studentId,
                        year = year,
                        month = month
                    )
                }
            } catch (e: Exception) {
                Log.e("failed to get NoticeDateList", e.message.toString())
            }
        }
    }

    fun getNotice(
        studentId: Long,
        year: String,
        month: String,
        date: String
    ){
        viewModelScope.launch {
            try {
                val response = repo.getNotice(studentId = studentId, year = year, month = month, date = date)

                if (response.isSuccessful) {
                    val gotNotice = response.body() ?: throw Exception("Notice 정보가 비어있습니다.")
                    _selectedNotice.update {
                        gotNotice
                    }
                    Log.d("selectedNoticeComment", gotNotice.toString())

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("Notice 데이터 가져오기 실패: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("failed to get Notice", e.message.toString())
            }
        }
    }

    fun updateTodayComment(
        studentId: Long,
        year: String,
        month: String,
        date: String,
        addCommentRequest: AddCommentRequest
    ){
        viewModelScope.launch {
            try {
                val response = repo.updateTodayComment(studentId = studentId, year = year, month = month, date = date, addCommentRequest = addCommentRequest)

                if(response.isSuccessful){
                    val updatedNoticeResponse = response.body() ?: throw Exception("Notice 업데이트 정보가 비어있습니다")

                    _selectedNotice.update {
                        updatedNoticeResponse
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("Notice 데이터 업데이트 실패: $errorBody")
                }

            } catch (e: Exception){
                Log.e("failed to update TodayComment", e.message.toString())
            }
        }
    }

    fun updateLTOComment(
        studentId: Long,
        year: String,
        month: String,
        date: String,
        stoId: Long,
        addCommentRequest: AddCommentRequest
    ){
        viewModelScope.launch {
            try {
                val response = repo.updateLTOComment(studentId = studentId, year = year, month = month, date = date, addCommentRequest = addCommentRequest, stoId = stoId)

                if(response.isSuccessful){
                    val updatedNoticeResponse = response.body() ?: throw Exception("Notice LTO Comment 업데이트 정보가 비어있습니다")

                    _selectedNoticeDetailList.update {currentNoticeDetailList ->
                        currentNoticeDetailList?.map {detail ->
                            if(detail.id == updatedNoticeResponse.id){
                                detail.copy(comment = updatedNoticeResponse.comment)
                            } else {
                                detail
                            }
                        }
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("Notice LTO Comment 데이터 업데이트 실패: $errorBody")
                }

            } catch (e: Exception){
                Log.e("failed to update LTOComment", e.message.toString())
            }
        }
    }

    fun addDetail(
        studentId: Long,
        year: String,
        month: String,
        date: String,
        stoId: Long
    ){

        Log.d("addDetailInfo", year+month+date)
        viewModelScope.launch {
            try {
                val response = repo.addDetail(studentId = studentId, year = year, month = month, date = date, stoId = stoId)

                if (response.isSuccessful) {
                    val newDetailResponse = response.body() ?: throw Exception("Detail 정보가 비어있습니다.")
//                    _selectedNoticeDetailList.update { currentSTOs ->
//                        currentSTOs?.let {
//                            // 현재 STO 리스트가 null이 아니면 새 STO를 추가
//                            it + DetailGraphResponse(
//                                id = newDetailResponse.id,
//                                comment = newDetailResponse.comment,
//                                stoId = newDetailResponse.stoId,
//                                noticeId = newDetailResponse.notice.id,
//                                ltoId = newDetailResponse.ltoId,
//                                dates = emptyList(),
//                                results = emptyList()
//                            )
//                        } ?: listOf(DetailGraphResponse(
//                            id = newDetailResponse.id,
//                            comment = newDetailResponse.comment,
//                            stoId = newDetailResponse.stoId,
//                            noticeId = newDetailResponse.notice.id,
//                            dates = emptyList(),
//                            results = emptyList(),
//                            ltoId = newDetailResponse.ltoId
//                        )) // 현재 STO 리스트가 null이면 새 리스트를 생성
//                    }

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
        year: String,
        month: String,
        date: String
    ){
        try {
            viewModelScope.launch {
                val response = repo.getDetailList(studentId = studentId, year = year, month = month, date = date)

                if (response.isSuccessful){
                    val gotDetailList = response.body() ?: throw  Exception("DetailList 정보가 비어있습니다.")
                    Log.d("gotDetailList", gotDetailList.toString())
                    _selectedNoticeDetailList.update {
                        gotDetailList
                    }
                }
            }
        } catch (e: Exception){
            Log.e("failed to get DetailList", e.message.toString())
        }
    }

    fun createSharePdf(
        studentId: Long,
        year: String,
        month: String,
        date: String,
    ) : String {
        viewModelScope.launch {
            repo.createSharePdf(studentId = studentId, year = year, month = month.toInt(), date = date)

        }
        return "http://192.168.35.225:8081/notices/${studentId}/reports?year=${year}&month=${month}&date=${date}"
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