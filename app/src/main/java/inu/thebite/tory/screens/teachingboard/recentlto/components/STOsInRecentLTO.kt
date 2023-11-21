package inu.thebite.tory.screens.teachingboard.recentlto.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.ui.theme.fontFamily_Inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun STOsInRecentLTO(
    modifier: Modifier = Modifier,
    dummyList : List<String>
){
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        dummyList.forEachIndexed { index, item ->
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = 15.dp, end = 15.dp, bottom = 30.dp, top = 3.dp)
                    .shadow(
                        elevation = 16.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000),
                        clip = false
                    )
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(size = 10.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item,
                        style = TextStyle(
                            fontSize = 22.sp,
                            lineHeight = 15.sp,
                            fontFamily = fontFamily_Inter,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF000000),
                        ),
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(6f)
                        .fillMaxWidth()
                ) {
                    DashboardGraph(
                        plusList = listOf(10f, 20f, 10f, 40f, 95f),
                        minusList = listOf(70f, 65f, 40f, 50f, 3f),
                        dateList = listOf("11/01","11/02","11/03","11/06","11/08")
                    )

                }
                Row(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1.5f)
                            .fillMaxHeight()
                            .padding(start = 10.dp, top = 10.dp)
                        ,
                        text = "메모",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 15.sp,
                            fontFamily = fontFamily_Inter,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF0047B3),
                        )
                    )
                    Surface(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(8.5f)
                            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 25.dp),
                        color = Color(0xFFD9D9D9),
                        shape = RoundedCornerShape(size = 5.dp)
                    ) {
                        BasicTextField(
                            modifier = Modifier.fillMaxSize().padding(5.dp),
                            value = "11/01 : 첫 시도\n\n11/02 : 개선됨\n\n11/03 : 감기로 인한 집중력 저하\n\n11/06 : 주도적인 성공 증가\n\n11/08 : 기준점 달성",
                            onValueChange = {},
                            readOnly = true,
                        )


                    }
                }
            }
        }
    }
}