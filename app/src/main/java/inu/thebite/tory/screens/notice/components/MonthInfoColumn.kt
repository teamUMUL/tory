package inu.thebite.tory.screens.notice.components

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
){
    val monthlyNoticeList by noticeViewModel.monthlyNotice.collectAsState()
    val selectedDate by noticeViewModel.selectedNoticeDate.collectAsState()
    val selectedNotice by noticeViewModel.selectedNotice.collectAsState()

    LaunchedEffect(Unit){
        selectedDate?.let { selectedDate ->
            noticeViewModel.getMonthlyNotice(studentId = selectedChild.id, year = selectedDate.year,month = selectedDate.month)
        }
    }

    LazyColumn{
        monthlyNoticeList?.let { monthlyNoticeList ->
            items(monthlyNoticeList) {notice ->
                NoticeItem(
                    selectedNotice = notice,
                    selectedChild = selectedChild,
                    noticeViewModel = noticeViewModel
                )

            }
        }
    }
}