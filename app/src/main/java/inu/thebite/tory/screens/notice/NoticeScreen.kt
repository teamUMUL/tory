package inu.thebite.tory.screens.notice

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.notice.components.NoticeDateColumn
import inu.thebite.tory.screens.notice.components.NoticeInfoColumn

@Composable
fun NoticeScreen(
    noticeViewModel: NoticeViewModel,
    stoViewModel: STOViewModel,
    ltoViewModel: LTOViewModel
) {
    val selectedNoticeDates by noticeViewModel.selectedNoticeDates.collectAsState()
    val selectedNoticeDate by noticeViewModel.selectedNoticeDate.collectAsState()
    val noticeYearAndMonthList by noticeViewModel.noticeYearAndMonthList.collectAsState()
    val selectedYear by noticeViewModel.selectedYear.collectAsState()
    val selectedMonth by noticeViewModel.selectedMonth.collectAsState()

    val noticeYearList by noticeViewModel.noticeYearList.collectAsState()
    val selectedNotice by noticeViewModel.selectedNotice.collectAsState()
    val noticeMonthList by noticeViewModel.noticeMonthList.collectAsState()
    val selectedNoticeDetailList by noticeViewModel.selectedNoticeDetailList.collectAsState()


    LaunchedEffect(Unit) {
//        selectedNoticeDates?.let {selectedNoticeDates ->
//            noticeViewModel.getNoticeDateList(studentId = 1L, year = extractYearsAndMonths(selectedNoticeDates).first.first(), month = extractYearsAndMonths(selectedNoticeDates).second.first())
//        }
        ltoViewModel.getAllLTOs(studentId = 1L)
        stoViewModel.getAllSTOs(studentId = 1L)
        noticeViewModel.getNoticeYearsAndMonths(studentId = 1L)
    }

    LaunchedEffect(noticeYearAndMonthList) {
        noticeViewModel.setNoticeYearList()
    }

    LaunchedEffect(selectedYear) {
        selectedYear?.let { noticeViewModel.setNoticeMonthList(selectedYear = it) }
//        noticeViewModel.clearSelectedMonth()
    }

    LaunchedEffect(selectedMonth) {
        selectedYear?.let {selectedYear ->
            selectedMonth?.let { selectedMonth ->
                noticeViewModel.getNoticeDateList(
                    studentId = 1L,
                    year = selectedYear,
                    month = selectedMonth
                )
            }
        }
    }

    LaunchedEffect(selectedNoticeDate) {
        selectedNoticeDate?.let { selectedNoticeDate ->
            noticeViewModel.getNotice(
                studentId = 1L,
                year = selectedNoticeDate.year,
                month = selectedNoticeDate.month.toString(),
                date = selectedNoticeDate.date
            )
            noticeViewModel.getDetailList(
                studentId = 1L,
                year = selectedNoticeDate.year,
                month = selectedNoticeDate.month.toString(),
                date = selectedNoticeDate.date
            )
        }
    }


    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Log.d("NoticeYearAndMonthList", noticeYearList.toString())
            Log.d("NoticeYearAndMonthList", noticeMonthList.toString())

            noticeYearList?.let { noticeYearList ->
                    NoticeDateColumn(
                        noticeYearList = noticeYearList,
                        noticeMonthList = noticeMonthList,
                        selectedNoticeDates = selectedNoticeDates,
                        selectedNoticeDate = selectedNoticeDate,
                        setSelectedDate = {
                            noticeViewModel.setSelectedNoticeDate(it)
                        },
                        selectedYear = selectedYear,
                        setSelectedYear = { noticeViewModel.setSelectedYear(it) },
                        selectedMonth = selectedMonth,
                        setSelectedMonth = { noticeViewModel.setSelectedMonth((it)) },
                    )
            } ?: Text(text = "데이터가 없습니다")

        }
        Divider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight(), color = Color.LightGray
        )
        Row(
            modifier = Modifier
                .weight(8f)
                .fillMaxHeight()
        ) {
            selectedNoticeDate?.let { selectedNoticeDate ->
                if (selectedNoticeDate.date.isNotEmpty()) {
                    selectedNoticeDetailList?.let { selectedNoticeDetailList ->
                        selectedNotice?.let { selectedNotice ->
                            NoticeInfoColumn(
                                selectedDate = selectedNoticeDate,
                                selectedNotice = selectedNotice,
                                selectedNoticeDetailList = selectedNoticeDetailList,
                                noticeViewModel = noticeViewModel,
                                stoViewModel = stoViewModel,
                                ltoViewModel = ltoViewModel
                            )
                        }
                    }
                }
            }

        }
    }

}


fun extractDate(input: String): String {
    // 연도를 포함한 처음 5자리를 건너뛰고, 그 다음 5자리 (월/일)를 가져옵니다.
    return input.substring(5, 10)
}


fun filterDatesByYearAndMonth(
    dateList: List<NoticeDate>,
    year: String,
    month: String
): List<NoticeDate> {
    return dateList.filter {
        it.year == year && it.month == month
    }
}

data class NoticeDate(
    val year: String,
    val month: String,
    val date: String,
    val day: String
)

data class NoticeYearMonth(
    val year: String,
    val month: String,
)

fun updateSelectedDateList(
    selectedYear: String,
    selectedMonth: String,
    dummyDateList: List<NoticeDate>,
    selectedDateList: MutableState<List<NoticeDate>>
) {
    selectedDateList.value = dummyDateList.filter {
        it.year == selectedYear && it.month == selectedMonth
    }
}