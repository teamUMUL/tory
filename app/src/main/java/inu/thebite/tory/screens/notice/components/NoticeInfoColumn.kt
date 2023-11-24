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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.notice.NoticeDate
import inu.thebite.tory.screens.notice.extractDate
import inu.thebite.tory.ui.theme.fontFamily_Inter


@Composable
fun NoticeInfoColumn(
    selectedDate: NoticeDate,
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF0047B3), Color(0xFF7F5AF0))
    )


    val dummySTOList = mutableListOf<StoResponse>().toMutableStateList()
    for (i in 1..8) {
        dummySTOList.add(
            StoResponse(
                id = i.toLong(),
                templateNum = i,
                status = "진행중",
                name = "같은 사진 매칭(${i} array)",
                contents = "",
                count = i,
                goal = i,
                goalPercent = i,
                achievementOrNot = "",
                urgeType = "",
                urgeContent = "",
                enforceContent = "",
                memo = "",
                hitGoalDate = "",
                registerDate = "",
                delYN = "",
                round = i,
                imageList = listOf(),
                pointList = listOf(),
                lto = LtoResponse(
                    id = i.toLong(),
                    templateNum = i,
                    status = "",
                    name = "더미용 LTO ${i}",
                    contents = "",
                    game = "",
                    achieveDate = "",
                    registerDate = "",
                    delYN = "",
                    domain = DomainResponse(
                        id = i.toLong(),
                        templateNum = i,
                        type = "",
                        status = "",
                        name = "더미용 Domain ${i}",
                        contents = "",
                        useYN = "",
                        delYN = "",
                        registerDate = ""
                    )
                )
            )
        )
    }
    val uniqueLTOList = dummySTOList.map { it.lto }.distinctBy { it.id }
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
                                .fillMaxSize()
                                .padding(5.dp),
                            value = "오늘은 LTO 1, LTO 2를 실시했습니다. \n오늘 실시한 LTO 1, LTO 2와 관련된 교육을 가정에서도 진행해주시면 더욱 효과적입니다. ",
                            onValueChange = {},
                            readOnly = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontFamily = fontFamily_Inter,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF0047B3),
                                letterSpacing = 0.16.sp,
                                textAlign = TextAlign.Start,
                            )
                        )
                    }
                }
            }
        }
        items(uniqueLTOList) { lto ->
            LTOItem(lto = lto)
        }
    }
}