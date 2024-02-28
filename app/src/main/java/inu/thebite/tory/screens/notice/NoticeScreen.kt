package inu.thebite.tory.screens.notice

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import co.yml.charts.common.extensions.isNotNull
import es.dmoral.toasty.Toasty
import inu.thebite.tory.R
import inu.thebite.tory.model.notice.DateResponse
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.screens.education.compose.sidebar.currentToast
import inu.thebite.tory.screens.education.screen.VerticalDivider
import inu.thebite.tory.screens.education.screen.clickableWithNoRipple
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.notice.components.MonthInfoColumn
import inu.thebite.tory.screens.notice.components.NoticeDateColumn
import inu.thebite.tory.screens.notice.components.NoticeInfoColumn
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildSelectViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoticeScreen(
    noticeViewModel: NoticeViewModel,
    stoViewModel: STOViewModel,
    ltoViewModel: LTOViewModel,
    navController: NavController,
    selectedChild: StudentResponse?,
    childSelectViewModel: ChildSelectViewModel
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val selectedNoticeDates by noticeViewModel.selectedNoticeDates.collectAsState()
    val selectedNoticeDate by noticeViewModel.selectedNoticeDate.collectAsState()
    val noticeYearAndMonthList by noticeViewModel.noticeYearAndMonthList.collectAsState()
    val selectedYear by noticeViewModel.selectedYear.collectAsState()
    val selectedMonth by noticeViewModel.selectedMonth.collectAsState()

    val noticeYearList by noticeViewModel.noticeYearList.collectAsState()
    val selectedNotice by noticeViewModel.selectedNotice.collectAsState()
    val noticeMonthList by noticeViewModel.noticeMonthList.collectAsState()
    val selectedNoticeDetailList by noticeViewModel.selectedNoticeDetailList.collectAsState()
    val pdfUrl by noticeViewModel.pdfUrl.collectAsState()

    val (noticePdfDialog, setNoticePdfDialog) = rememberSaveable {
        mutableStateOf(false)
    }

    val (monthNotice, setMonthNotice) = rememberSaveable {
        mutableStateOf(false)
    }

    if (noticePdfDialog){
        pdfUrl?.let { pdfUrl ->
            if (!monthNotice){
                AlertDialog(
                    onDismissRequest = { setNoticePdfDialog(false) },
                    text = {
                        AndroidView(
                            factory = { context ->
                                WebView(context).apply {
                                    webViewClient = WebViewClient()
                                    settings.javaScriptEnabled = true
                                    loadUrl(pdfUrl)
                                }
                            },
                        )
                    },
                    confirmButton = {
                        Button(onClick = {
                            setNoticePdfDialog(false)
                            noticeViewModel.clearPdfUrl()
                        }) {
                            Text("닫기")
                        }
                    },
                    properties = DialogProperties(
                        usePlatformDefaultWidth = false
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .fillMaxHeight(),
                    containerColor = Color.White
                )
            } else {
                currentToast?.cancel()
                val newToast = Toasty.warning(context, "월간 보고서는 개발중입니다.", Toast.LENGTH_SHORT, true)
                newToast.show()
                currentToast = newToast
                setNoticePdfDialog(false)
            }

        }

    }

    LaunchedEffect(Unit) {
//        selectedNoticeDates?.let {selectedNoticeDates ->
//            noticeViewModel.getNoticeDateList(studentId = 1L, year = extractYearsAndMonths(selectedNoticeDates).first.first(), month = extractYearsAndMonths(selectedNoticeDates).second.first())
//        }
        selectedChild?.let { selectedChild ->
            ltoViewModel.getAllLTOs(studentId = selectedChild.id)
            stoViewModel.getAllSTOs(studentId = selectedChild.id)
            noticeViewModel.getNoticeYearsAndMonths(studentId = selectedChild.id)
        }

    }

    LaunchedEffect(selectedChild) {

        selectedChild?.let { selectedChild ->
            Log.d("clearAll", selectedChild.name.toString())

//            noticeViewModel.clearAll()
            ltoViewModel.getAllLTOs(studentId = selectedChild.id)
            stoViewModel.getAllSTOs(studentId = selectedChild.id)
            noticeViewModel.getNoticeYearsAndMonths(studentId = selectedChild.id)
        }
    }

    LaunchedEffect(noticeYearAndMonthList) {
        noticeViewModel.setNoticeYearList()
    }

    LaunchedEffect(selectedYear) {
        selectedYear?.let { noticeViewModel.setNoticeMonthList(selectedYear = it) }
//        noticeViewModel.clearSelectedMonth()
    }

    LaunchedEffect(selectedMonth) {
        selectedYear?.let { selectedYear ->
            selectedMonth?.let { selectedMonth ->
                selectedChild?.let { selectedChild ->
                    noticeViewModel.getNoticeDateList(
                        studentId = selectedChild.id,
                        year = selectedYear,
                        month = selectedMonth
                    )
                }
            }
        }
    }

    LaunchedEffect(selectedNoticeDate) {
        selectedNoticeDate?.let { selectedNoticeDate ->
            selectedChild?.let { selectedChild ->
                noticeViewModel.getNotice(
                    studentId = selectedChild.id,
                    year = selectedNoticeDate.year,
                    month = selectedNoticeDate.month.toString(),
                    date = selectedNoticeDate.date
                )
                noticeViewModel.getDetailList(
                    studentId = selectedChild.id,
                    year = selectedNoticeDate.year,
                    month = selectedNoticeDate.month.toString(),
                    date = selectedNoticeDate.date
                )
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    // TextField 외부를 탭했을 때만 포커스 해제
                    focusManager.clearFocus()
                    keyboardController?.hide()
                })
            }
    ) {
        Divider(thickness = 2.dp, color = Color.LightGray)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight()
                    .background(
                        color = Color(0xFFFBFAF7),
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_export),
                        contentDescription = null,
                        tint = Color(0xFF8E8E8E),
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .size(40.dp)
                            .clickableWithNoRipple {
                                if (selectedChild.isNotNull() && selectedNoticeDate.isNotNull()){
                                    noticeViewModel.createSharePdf(
                                        studentId = selectedChild!!.id,
                                        year = selectedNoticeDate!!.year,
                                        month = selectedNoticeDate!!.month.toString(),
                                        date = selectedNoticeDate!!.date
                                    )
                                    setNoticePdfDialog(true)
                                } else {
                                    Toasty.warning(context, "보고서를 선택해주세요", Toast.LENGTH_SHORT, true).show()
                                }

                            }
                    )
                }
            }
            Divider(
                modifier = Modifier
                    .width(2.dp)
                    .fillMaxHeight(),
                color = Color.LightGray
            )
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
                            setMonthNotice(false)
                        },
                        selectedYear = selectedYear,
                        setSelectedYear = { noticeViewModel.setSelectedYear(it) },
                        selectedMonth = selectedMonth,
                        setSelectedMonth = { noticeViewModel.setSelectedMonth((it)) },
                        noticeViewModel = noticeViewModel,
                        navController = navController,
                        selectedChild = selectedChild,
                        setMonthNotice = {
//                            noticeViewModel.clearSelectedNotice()
                            setMonthNotice(true)
                        },
                        monthNotice = monthNotice
                    )
                } ?: Text(text = "데이터가 없습니다")
            }
            Divider(
                modifier = Modifier
                    .width(2.dp)
                    .fillMaxHeight(),
                color = Color.LightGray
            )
            Row(
                modifier = Modifier
                    .weight(7.5f)
                    .fillMaxHeight()
                    .background(Color.White)
            ) {
                if (monthNotice){

                    selectedChild?.let {selectedChild ->
                        MonthInfoColumn(
                            noticeViewModel = noticeViewModel,
                            selectedChild = selectedChild
                        )
                    }
                } else {
                    selectedNoticeDate?.let { selectedNoticeDate ->
                        if (selectedNoticeDate.date.isNotEmpty()) {
                            selectedNoticeDetailList?.let { selectedNoticeDetailList ->
                                selectedNotice?.let { selectedNotice ->
                                    selectedChild?.let { selectedChild ->
                                        NoticeInfoColumn(
                                            selectedDate = selectedNoticeDate,
                                            selectedNotice = selectedNotice,
                                            selectedChild = selectedChild,
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