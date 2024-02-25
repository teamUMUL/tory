package inu.thebite.tory.screens.notice.components

import android.util.Log
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import inu.thebite.tory.model.notice.AddCommentRequest
import inu.thebite.tory.model.notice.DateResponse
import inu.thebite.tory.model.notice.NoticeResponse
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.screens.education.screen.clickableWithNoRipple
import inu.thebite.tory.screens.notice.NoticeViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NoticeItem(
    selectedNotice: NoticeResponse,
    selectedChild: StudentResponse,
    noticeViewModel: NoticeViewModel,
){
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF0047B3), Color(0xFF0047B3))
    )

    val isTodayCommentReadOnly = remember {
        mutableStateOf(true)
    }
    var todayComment by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(selectedNotice.comment))
    }
    LaunchedEffect(selectedNotice.comment){
        todayComment = TextFieldValue(selectedNotice.comment)
    }

    LaunchedEffect(Unit){
        todayComment = TextFieldValue(selectedNotice.comment)
    }

    val coroutineScope = rememberCoroutineScope()

    val focusRequester = FocusRequester()
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
                        .weight(0.26f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${selectedNotice.month}/${selectedNotice.date}",
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
                        text = "요약",
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
                    onValueChange = {newValue ->
                        // 사용자 입력에 기반하여 TextFieldValue를 업데이트하되, 커서 위치를 직접 조정하지 않습니다.
                        // 사용자가 입력한 새로운 값과 커서 위치를 포함한 새로운 TextFieldValue 객체를 생성합니다.
                        todayComment = newValue.copy(
                            text = newValue.text,
                            selection = newValue.selection
                        )
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
                        autoCorrect = true, keyboardType = KeyboardType.Text, imeAction = ImeAction.Default
                    ),
//                    onTextLayout = {textLayoutResult ->
//                        if (todayComment.selection.collapsed && todayComment.selection.start != textLayoutResult.lineCount) {
//                            // 커서를 텍스트의 끝으로 이동
//                            todayComment = todayComment.copy(selection = TextRange(todayComment.text.length))
//                        }
//                    }
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
                    Row {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    noticeViewModel.createMonthlyNoticeAutoComment(studentId = selectedChild.id, year = selectedNotice.year, month = selectedNotice.month, date = selectedNotice.date)
                                    todayComment = TextFieldValue(selectedNotice.comment)
                                }

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
                                text = "자동생성",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    lineHeight = 15.sp,
                                    fontFamily = fontFamily_Lato,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFFFFFF),
                                )
                            )
                        }
                        Button(
                            onClick = {
                                isTodayCommentReadOnly.value = !isTodayCommentReadOnly.value
                                noticeViewModel.updateTodayComment(
                                    studentId = selectedChild.id,
                                    addCommentRequest = AddCommentRequest(
                                        comment = todayComment.text
                                    ),
                                    year = selectedNotice.year,
                                    month = selectedNotice.month.toString(),
                                    date = selectedNotice.date
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
}