package inu.thebite.tory.screens.notice.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import inu.thebite.tory.screens.education.screen.clickableWithNoRipple
import inu.thebite.tory.screens.notice.NoticeViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato

@Composable
fun NoticeItemTextField(
    noticeViewModel: NoticeViewModel
){

    val isLTOCommentReadOnly = remember {
        mutableStateOf(true)
    }

    var ltoComment by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("오늘은 LTO 1, LTO 2를 실시했습니다. \n오늘 실시한 LTO 1, LTO 2와 관련된 교육을 가정에서도 진행해주시면 더욱 효과적입니다. "))
    }
    val focusRequester = FocusRequester()
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
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
            var ltoMemo by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(TextFieldValue())
            }
//                    ltoMemo = TextFieldValue("오늘은 LTO 1, LTO 2를 실시했습니다. \n오늘 실시한 LTO 1, LTO 2와 관련된 교육을 가정에서도 진행해주시면 더욱 효과적입니다. ",)
            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp)
                    .focusRequester(focusRequester),
                value = ltoComment,
                onValueChange = {
                    ltoComment = it
                },
                readOnly = isLTOCommentReadOnly.value,
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
                    if (ltoComment.selection.collapsed && ltoComment.selection.start != textLayoutResult.lineCount) {
                        // 커서를 텍스트의 끝으로 이동
                        ltoComment = ltoComment.copy(selection = TextRange(ltoComment.text.length))
                    }
                }
            )
            if (isLTOCommentReadOnly.value){
                Box(
                    modifier = Modifier
                        .clickableWithNoRipple {
                            isLTOCommentReadOnly.value = !isLTOCommentReadOnly.value
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
                        isLTOCommentReadOnly.value = !isLTOCommentReadOnly.value
//                        noticeViewModel.updateLTOComment(studentId = 1L, date = "2023/11/01 (월)", stoId = )
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