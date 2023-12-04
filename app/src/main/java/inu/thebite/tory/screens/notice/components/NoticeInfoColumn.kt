package inu.thebite.tory.screens.notice.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.R
import inu.thebite.tory.model.detail.DetailResponse
import inu.thebite.tory.model.notice.AddCommentRequest
import inu.thebite.tory.screens.education.screen.clickableWithNoRipple
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.notice.NoticeDate
import inu.thebite.tory.screens.notice.NoticeViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato


@Composable
fun NoticeInfoColumn(
    selectedDate: NoticeDate,
    selectedNoticeDetailList :  List<DetailResponse>,
    noticeViewModel: NoticeViewModel,
    stoViewModel: STOViewModel
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF0047B3), Color(0xFF7F5AF0))
    )

    val isTodayCommentReadOnly = remember {
        mutableStateOf(true)
    }

    var todayComment by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(selectedNoticeDetailList.first().comment))
    }
    val focusRequester = FocusRequester()

//    val dummySTOList = mutableListOf<StoResponse>().toMutableStateList()
//    for (i in 1..8) {
//        dummySTOList.add(
//            StoResponse(
//                id = i.toLong(),
//                templateNum = i,
//                status = "진행중",
//                name = "같은 사진 매칭(${i} array)",
//                contents = "",
//                count = i,
//                goal = i,
//                goalPercent = i,
//                achievementOrNot = "",
//                urgeType = "",
//                urgeContent = "",
//                enforceContent = "",
//                memo = "",
//                hitGoalDate = "",
//                registerDate = "",
//                delYN = "",
//                round = i,
//                imageList = listOf(),
//                pointList = listOf(),
//                lto = LtoResponse(
//                    id = i.toLong(),
//                    templateNum = i,
//                    status = "",
//                    name = "더미용 LTO ${i}",
//                    contents = "",
//                    game = "",
//                    achieveDate = "",
//                    registerDate = "",
//                    delYN = "",
//                    domain = DomainResponse(
//                        id = i.toLong(),
//                        templateNum = i,
//                        type = "",
//                        status = "",
//                        name = "더미용 Domain ${i}",
//                        contents = "",
//                        useYN = "",
//                        delYN = "",
//                        registerDate = ""
//                    )
//                )
//            )
//        )
//    }
    val selectedNoticeSTOList = stoViewModel.findSTOsByIds(selectedNoticeDetailList.map { it.stoId })
    val uniqueLTOList = selectedNoticeSTOList.map { it.ltoId }.distinctBy { it.id }
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .padding(horizontal = 25.dp, vertical = 8.dp)
                    .shadow(
                        elevation = 16.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000),
                        clip = false
                    )
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(color = Color(0xFFE7EBF0), shape = RoundedCornerShape(size = 10.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2.7f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .fillMaxHeight()
                            .shadow(
                                elevation = 4.dp,
                                spotColor = Color(0x40000000),
                                ambientColor = Color(0x40000000),
                                clip = false
                            )
                            .background(
                                color = Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(size = 10.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = gradient,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .fillMaxHeight()
                                .weight(0.22f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${selectedDate.month}/${selectedDate.date}",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    lineHeight = 24.sp,
                                    fontFamily = fontFamily_Inter,
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFFFFFFFF),
                                    letterSpacing = 0.24.sp,
                                )

                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(0.56f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "오늘의 총평",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    lineHeight = 24.sp,
                                    fontFamily = fontFamily_Inter,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF000000),

                                    letterSpacing = 0.24.sp,
                                )
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(0.22f),
                            contentAlignment = Alignment.Center
                        ) {
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(7.3f),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 35.dp, bottom = 30.dp, end = 35.dp, top = 0.dp)
                            .fillMaxSize()
                            .border(
                                width = 2.dp,
                                color = Color(0xFF0047B3),
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                            .background(
                                color = Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .weight(1f)
                                .padding(5.dp)
                                .focusRequester(focusRequester),
                            value = todayComment,
                            onValueChange = {
                                todayComment = it
                            },
                            readOnly = isTodayCommentReadOnly.value,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontFamily = fontFamily_Inter,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF0047B3),
                                letterSpacing = 0.16.sp,
                                textAlign = TextAlign.Start,
                            ),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.None,
                                autoCorrect = true, keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                            ),
                            onTextLayout = {textLayoutResult ->
                                if (todayComment.selection.collapsed && todayComment.selection.start != textLayoutResult.lineCount) {
                                    // 커서를 텍스트의 끝으로 이동
                                    todayComment = todayComment.copy(selection = TextRange(todayComment.text.length))
                                }
                            }
                        )
                        if (isTodayCommentReadOnly.value){
                            Box(
                                modifier = Modifier
                                    .clickableWithNoRipple {
                                        isTodayCommentReadOnly.value = !isTodayCommentReadOnly.value
                                        focusRequester.requestFocus()
                                    }
                                    .padding(top = 15.dp, end = 15.dp)
                                    .size(32.dp)
                                    .background(color = Color(0xFF7F5AF0), shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ){
                                    Icon(painter = painterResource(id = R.drawable.icon_write), contentDescription = null, tint = Color.White)

                            }
                        } else {
                            Button(
                                onClick = {
                                    isTodayCommentReadOnly.value = !isTodayCommentReadOnly.value
                                    noticeViewModel.updateTodayComment(
                                        studentId = 1L,
                                        date = "2023/11/01 (월)",
                                        addCommentRequest = AddCommentRequest(
                                            comment = todayComment.text
                                        )
                                    )
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF7F5AF0),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .padding(top = 15.dp, end = 15.dp)
                            ){
                                Text(
                                    text = "저장하기",
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        lineHeight = 15.sp,
                                        fontFamily = fontFamily_Lato,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFFFFFFF),
                                    )
                                )
                            }

                        }

                    }
                }
            }
        }
        items(uniqueLTOList) { lto ->
            LTOItem(
                lto = lto,
                noticeViewModel = noticeViewModel
            )
        }
    }
}