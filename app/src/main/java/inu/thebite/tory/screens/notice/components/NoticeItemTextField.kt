package inu.thebite.tory.screens.notice.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.ui.theme.fontFamily_Inter

@Composable
fun NoticeItemTextField(

){
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
                    .fillMaxSize()
                    .padding(5.dp),
                value = ltoMemo,
                onValueChange = { ltoMemo = it },
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
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
            )
        }
    }
}