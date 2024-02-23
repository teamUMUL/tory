package inu.thebite.tory.screens.notice.components

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.screens.notice.NoticeViewModel

@Composable
fun MonthInfoColumn(
    noticeViewModel: NoticeViewModel,
    selectedChild: StudentResponse,
) {
    val monthlyNoticeList by noticeViewModel.monthlyNotice.collectAsState()
    val selectedYear by noticeViewModel.selectedYear.collectAsState()
    val selectedMonth by noticeViewModel.selectedMonth.collectAsState()

    LaunchedEffect(Unit) {
        Log.d("gottenMonthlyNotice", "startMonthlyNotice")
        selectedYear?.let { selectedYear ->
            selectedMonth?.let {selectedMonth ->
                noticeViewModel.getMonthlyNotice(
                    studentId = selectedChild.id,
                    year = selectedYear,
                    month = selectedMonth
                )
            }
        }
    }

    LazyColumn {
        monthlyNoticeList?.let { monthlyNoticeList ->
            items(monthlyNoticeList) { notice ->
                NoticeItem(
                    selectedNotice = notice,
                    selectedChild = selectedChild,
                    noticeViewModel = noticeViewModel,
                )

            }
        }
    }
}