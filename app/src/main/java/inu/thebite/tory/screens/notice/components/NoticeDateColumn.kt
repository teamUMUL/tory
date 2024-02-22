package inu.thebite.tory.screens.notice.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.ParcelFileDescriptor
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.net.toUri
import androidx.navigation.NavController
import inu.thebite.tory.R
import inu.thebite.tory.model.notice.DateResponse
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.screens.education.screen.clickableWithNoRipple
import inu.thebite.tory.screens.notice.NoticeDate
import inu.thebite.tory.screens.notice.NoticeViewModel
import inu.thebite.tory.ui.theme.fontFamily_Lato


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun NoticeDateColumn(
    selectedNoticeDates: List<DateResponse>?,
    noticeYearList: List<String>,
    noticeMonthList: List<String>?,
    selectedNoticeDate: DateResponse?,
    setSelectedDate: (DateResponse) -> Unit,
    selectedYear: String?,
    setSelectedYear: (String) -> Unit,
    selectedMonth: String?,
    setSelectedMonth: (String) -> Unit,
    noticeViewModel: NoticeViewModel,
    navController: NavController,
    selectedChild: StudentResponse?,
    setMonthNotice : () -> Unit,
    monthNotice: Boolean
) {
    val context = LocalContext.current

    val (noticePdfDialog, setNoticePdfDialog) = remember {
        mutableStateOf(false)
    }

    val noticeURL by noticeViewModel.pdfUrl.collectAsState()

    if (noticePdfDialog) {
        noticeURL?.let { noticeURL ->
            Dialog(
                properties = DialogProperties(
                    usePlatformDefaultWidth = false,
                ),
                onDismissRequest = { setNoticePdfDialog(false) }
            ) {
                AndroidView(factory = { context ->
                    WebView(context).apply {
                        webViewClient = WebViewClient()
                        loadUrl(noticeURL)
                    }
                })
            }
        }
    }

    val isYearExpanded = remember {
        mutableStateOf(false)
    }
    val isMonthExpanded = remember {
        mutableStateOf(false)
    }



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFFBFAF7),
            )
    ) {
        item {
            Text(
                text = "보고서",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    fontFamily = fontFamily_Lato,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF1D1C1D),
                ),
                modifier = Modifier.padding(start = 15.dp, top = 8.dp, bottom = 8.dp)
            )
            Divider(thickness = 1.dp, color = Color.LightGray)
        }
        item {
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 0.dp, end = 0.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                ExposedSelector(
                    isExpanded = isYearExpanded,
                    selectedString = selectedYear,
                    itemList = noticeYearList,
                    onClick = { setSelectedYear(it) },
                    width = 70.dp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "년")
                Spacer(modifier = Modifier.width(10.dp))
                ExposedSelector(
                    isExpanded = isMonthExpanded,
                    selectedString = selectedMonth,
                    itemList = noticeMonthList,
                    onClick = { setSelectedMonth(it) },
                    width = 50.dp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "월")
            }
        }
        item {
            Text(
                text = "${selectedYear ?: ""}년 ${selectedMonth ?: ""}월",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    fontFamily = fontFamily_Lato,
                    fontWeight = FontWeight(400),
                    color = Color.Black,
                ),
                modifier = Modifier
                    .padding(start = 15.dp, top = 9.dp, bottom = 9.dp)
            )
        }

        item {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .clickable {
                        setMonthNotice()
                    }
                    .background(
                        if (monthNotice) Color(0xFF1264A3) else Color.Transparent
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row{
                    selectedYear?.let { selectedYear ->
                        selectedMonth?.let { selectedMonth ->
                            Text(
                                text = "#",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    lineHeight = 22.sp,
                                    fontFamily = fontFamily_Lato,
                                    fontWeight = FontWeight(400),
                                    color = if (monthNotice) Color.White else Color.Black,
                                ),
                                modifier = Modifier
                                    .padding(start = 20.dp, end = 0.dp, top = 4.dp, bottom = 4.dp)
                            )
                            Text(
                                text = "${selectedYear ?: ""}/ ${selectedMonth ?: ""}월 보고서",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    lineHeight = 22.sp,
                                    fontFamily = fontFamily_Lato,
                                    fontWeight = FontWeight(400),
                                    color = if (monthNotice) Color.White else Color.Black,
                                ),
                                modifier = Modifier
                                    .padding(vertical = 4.dp, horizontal = 10.dp)
                            )
                        }
                    }

                }
            }
            Divider(thickness = 1.dp, color = Color(0xFFD3D3D3))
        }
        selectedNoticeDates?.let { selectedNoticeDates ->
            items(selectedNoticeDates) { date ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .clickable { setSelectedDate(date) }
                        .background(
                            if (selectedNoticeDate == date && !monthNotice) Color(0xFF1264A3) else Color.Transparent
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Text(
                            text = "#",
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 22.sp,
                                fontFamily = fontFamily_Lato,
                                fontWeight = FontWeight(400),
                                color = if (selectedNoticeDate == date && !monthNotice) Color.White else Color.Black,
                            ),
                            modifier = Modifier
                                .padding(start = 20.dp, end = 0.dp, top = 4.dp, bottom = 4.dp)
                        )
                        Text(
                            text = "${date.year}/${date.month}/${date.date} (${date.day})",
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 22.sp,
                                fontFamily = fontFamily_Lato,
                                fontWeight = FontWeight(400),
                                color = if (selectedNoticeDate == date && !monthNotice) Color.White else Color.Black,
                            ),
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 10.dp)
                        )
                    }

//                    if (selectedNoticeDate == date) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.icon_export),
//                            contentDescription = null,
//                            tint = Color.White,
//                            modifier = Modifier
//                                .size(40.dp)
//                                .padding(end = 20.dp)
//                                .clickableWithNoRipple {
//                                    selectedChild?.let {selectedChild ->
//                                        noticeViewModel.createSharePdf(
//                                            studentId = selectedChild.id,
//                                            year = selectedNoticeDate.year,
//                                            month = selectedNoticeDate.month.toString(),
//                                            date = selectedNoticeDate.date
//                                        )
//                                    }
//                                    setNoticePdfDialog(true)
//                                }
//                        )
//                    }
                }
            }
        }
    }
}

fun extractYearsAndMonths(dateList: List<NoticeDate>): Pair<List<String>, List<String>> {
    val years = mutableSetOf<Int>()
    val months = mutableSetOf<Int>()

    dateList.forEach { date ->
        years.add(date.year.toInt())
        months.add(date.month.toInt())
    }

    // 중복 제거 후 내림차순으로 정렬
    val sortedYears = years.sortedDescending()
    val sortedMonths = months.sortedDescending()

    return sortedYears.map { it.toString() } to sortedMonths.map { it.toString() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedSelector(
    isExpanded: MutableState<Boolean>,
    selectedString: String?,
    itemList: List<String>?,
    width: Dp,
    onClick: (String) -> Unit
) {
    ExposedDropdownMenuBox(
        modifier = Modifier,
        expanded = isExpanded.value,
        onExpandedChange = { isExpanded.value = !isExpanded.value }
    ) {
        Box(
            modifier = Modifier
                .width(width)
                .height(30.dp)
                .border(
                    width = 0.8.dp,
                    color = Color(0xFFE2E2E2),
                    shape = RoundedCornerShape(size = 5.dp)
                )
                .menuAnchor()
                .padding(start = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BasicTextField(
                    value = selectedString ?: "",
                    onValueChange = {},
                    readOnly = true,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontFamily = fontFamily_Lato,
                        fontWeight = FontWeight(400),
                        textAlign = TextAlign.Start,
                        lineHeight = 22.sp
                    ),
                    modifier = Modifier
                        .width(width = width - 30.dp),
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }

        }
        itemList?.let { itemList ->
            ExposedDropdownMenu(
                expanded = isExpanded.value,
                onDismissRequest = { isExpanded.value = false },
                modifier = Modifier
            ) {
                itemList.distinct().forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(text = item)
                        },
                        onClick = {
                            onClick(item)
                            isExpanded.value = false
                        },
                        modifier = Modifier.height(30.dp)
                    )
                }
            }
        }

    }
}

fun downloadPdf(url: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(url.toUri(), "application/pdf")
        flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        // PDF를 여는 앱이 없을 경우의 처리
    }
}
