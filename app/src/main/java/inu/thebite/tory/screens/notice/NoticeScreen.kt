package inu.thebite.tory.screens.notice

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.notice.components.NoticeDateColumn
import inu.thebite.tory.screens.notice.components.NoticeInfoColumn
import inu.thebite.tory.ui.theme.fontFamily_Lato

@Composable
fun NoticeScreen(
    noticeViewModel: NoticeViewModel,
    stoViewModel: STOViewModel
) {
    val selectedNoticeDates by noticeViewModel.selectedNoticeDates.collectAsState()
    val selectedNoticeDate by noticeViewModel.selectedNoticeDate.collectAsState()
    val selectedNoticeDetailList by noticeViewModel.selectedNoticeDetailList.collectAsState()

    var selectedYear by remember { mutableStateOf("") }
    var selectedMonth by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
//        selectedNoticeDates?.let {selectedNoticeDates ->
//            noticeViewModel.getNoticeDateList(studentId = 1L, year = extractYearsAndMonths(selectedNoticeDates).first.first(), month = extractYearsAndMonths(selectedNoticeDates).second.first())
//        }
    }

    LaunchedEffect(selectedNoticeDate) {
        selectedNoticeDate?.let {selectedNoticeDate ->
            noticeViewModel.getNoticeDateList(studentId = 1L, year = selectedNoticeDate.year, month = selectedNoticeDate.month)
            noticeViewModel.getDetailList(studentId = 1L, date = "${selectedNoticeDate.year}/${selectedNoticeDate.month}/${selectedNoticeDate.date} ${selectedNoticeDate.day}")
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
            selectedNoticeDates?.let { selectedNoticeDates ->
                selectedNoticeDate?.let {
                    NoticeDateColumn(
                        selectedNoticeDates = selectedNoticeDates,
                        selectedDate = it,
                        setSelectedDate = {  },
                        selectedYear = selectedYear,
                        setSelectedYear = { selectedYear = it },
                        selectedMonth = selectedMonth,
                        setSelectedMonth = { selectedMonth = it },
                    )
                }
            } ?: Text(
                    text = "보고서가 존재하지 않습니다",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = fontFamily_Lato,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF1D1C1D),
                    )
                )
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
                    selectedNoticeDetailList?.let {selectedNoticeDetailList ->
                        NoticeInfoColumn(
                            selectedDate = selectedNoticeDate,
                            selectedNoticeDetailList = selectedNoticeDetailList,
                            noticeViewModel = noticeViewModel,
                            stoViewModel = stoViewModel
                        )
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