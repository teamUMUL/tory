package inu.thebite.tory.screens.notice

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import inu.thebite.tory.screens.notice.components.NoticeDateColumn
import inu.thebite.tory.screens.notice.components.NoticeInfoColumn
import inu.thebite.tory.screens.notice.components.extractYearsAndMonths

@Composable
fun NoticeScreen(
    noticeViewModel: NoticeViewModel
) {
    val selectedNoticeDates by noticeViewModel.selectedNoticeDates.collectAsState()

    val dummyDateList = mutableListOf<NoticeDate>()
    for (i in 1..10) {
        for (j in 1..20) {
            dummyDateList.add(
                NoticeDate(
                    year = "2023",
                    month = i.toString(),
                    date = j.toString(),
                    day = "월"
                )
            )
        }
    }
    val (dummyYearList, dummyMonthList) = extractYearsAndMonths(dummyDateList)

    var selectedDate by remember { mutableStateOf(dummyDateList.last()) }
    var selectedYear by remember { mutableStateOf(dummyYearList.first()) }
    var selectedMonth by remember { mutableStateOf(dummyMonthList.first()) }

    LaunchedEffect(Unit){
        noticeViewModel.setSelectedNoticeDates(
            allDates = dummyDateList,
            selectedYear = selectedYear,
            selectedMonth = selectedMonth
        )
    }

    LaunchedEffect(selectedYear, selectedMonth){
        noticeViewModel.setSelectedNoticeDates(
            allDates = dummyDateList,
            selectedYear = selectedYear,
            selectedMonth = selectedMonth
        )
    }


    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
        ) {
            selectedNoticeDates?.let {selectedNoticeDates ->
                NoticeDateColumn(
                    dummyMonthList = dummyMonthList,
                    dummyYearList = dummyYearList,
                    selectedDateList = selectedNoticeDates,
                    selectedDate = selectedDate,
                    setSelectedDate = { selectedDate = it },
                    selectedYear = selectedYear,
                    setSelectedYear = { selectedYear = it },
                    selectedMonth = selectedMonth,
                    setSelectedMonth = { selectedMonth = it }
                )
            }
        }
        Divider(modifier = Modifier
            .width(1.dp)
            .fillMaxHeight(), color = Color.LightGray)
        Row(
            modifier = Modifier
                .weight(8f)
                .fillMaxHeight()
        ) {
            if (selectedDate.date.isNotEmpty()) {
                NoticeInfoColumn(selectedDate = selectedDate)
            }
        }
    }

}



fun extractDate(input: String): String {
    // 연도를 포함한 처음 5자리를 건너뛰고, 그 다음 5자리 (월/일)를 가져옵니다.
    return input.substring(5, 10)
}



fun filterDatesByYearAndMonth(dateList: List<NoticeDate>, year: String, month: String): List<NoticeDate> {
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