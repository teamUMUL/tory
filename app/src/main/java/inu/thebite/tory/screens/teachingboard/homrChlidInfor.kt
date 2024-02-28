package inu.thebite.tory.screens.teachingboard

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.R
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildSelectViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter

@SuppressLint("SuspiciousIndentation")
@Composable
fun childInfor(
    modifier: Modifier = Modifier,
    childSelectViewModel: ChildSelectViewModel
) {
    val selectedChildInfo by childSelectViewModel.selectedChildInfo.collectAsState()
    val fontSize = 17.sp

    val verticalScrollState = rememberScrollState()

    Row(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000)
            )
//            .width(241.dp)
//            .height(440.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, bottom = 15.dp, end = 25.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            pieChartPreview(modifier = Modifier.weight(0.75f))
//                Image(modifier = Modifier
//                    .width(180.dp)
//                    .height(180.dp),
//                    painter = painterResource(id = R.drawable.chart_round),
//                    contentDescription ="null",
//                    contentScale = ContentScale.Fit)
            Column(
                modifier = Modifier
                    .weight(1.25f)
                    .padding(top = 30.dp)
                    .verticalScroll(verticalScrollState),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "이름",
                    style = TextStyle(
                        fontSize = fontSize,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF0047B3)
                    )
                )
                Text(
                    text = selectedChildInfo?.name ?: "",
                    style = TextStyle(
                        fontSize = fontSize,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),

                        )
                )
                Text(
                    text = "생년월일",
                    style = TextStyle(
                        fontSize = fontSize,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF0047B3),

                        )
                )
                Text(
                    text = selectedChildInfo?.birth ?: "",
                    style = TextStyle(
                        fontSize = fontSize,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),

                        )
                )
                Text(
                    text = "프로그램 기간",
                    style = TextStyle(
                        fontSize = fontSize,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF0047B3),
                    )
                )
                Text(
                    text = "${selectedChildInfo?.startDate ?: ""}${selectedChildInfo?.let { "~" } ?: run {""}}${selectedChildInfo?.endDate ?: ""}",
                    style = TextStyle(
                        fontSize = fontSize,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                    )
                )
                Text(
                    text = "특징",
                    style = TextStyle(
                        fontSize = fontSize,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF0047B3),
                    )
                )
                Text(
                    text = selectedChildInfo?.etc ?: "",
                    style = TextStyle(
                        fontSize = fontSize,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                    )
                )
            }
        }
    }

}

@Composable
fun reportList() {
    Row(
        Modifier
            .padding(top = 4.dp)
            .fillMaxWidth()
            .height(78.dp)
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .clickable { }
                .height(70.dp)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(size = 10.dp)
                ),
                painter = painterResource(id = R.drawable.backgroun_btn),
                contentDescription = "null")
            Text(
                style = TextStyle(color = Color(0xFF0047B3), fontSize = 20.sp),
                fontWeight = FontWeight(400),
                text = "상담일지"
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .height(70.dp)
                .clickable { }
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(size = 10.dp)
                ),
                painter = painterResource(id = R.drawable.backgroun_btn),
                contentDescription = "null")
            Text(
                style = TextStyle(color = Color(0xFF0047B3), fontSize = 20.sp),
                fontWeight = FontWeight(400),
                text = "아동영상"
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .height(70.dp)
                .clickable { }
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(size = 10.dp)
                ),
                painter = painterResource(id = R.drawable.backgroun_btn),
                contentDescription = "null")
            Text(
                style = TextStyle(color = Color(0xFF0047B3), fontSize = 20.sp),
                fontWeight = FontWeight(400),
                text = "완료목록"
            )
        }

        Spacer(modifier = Modifier.width(15.dp))
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .height(70.dp)
                .clickable { }
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(size = 10.dp)
                ),
                painter = painterResource(id = R.drawable.backgroun_btn),
                contentDescription = "null")
            Text(
                style = TextStyle(color = Color(0xFF0047B3), fontSize = 20.sp),
                fontWeight = FontWeight(400),
                text = "그래프"
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .height(70.dp)
                .clickable { }
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(size = 10.dp)
                ),
                painter = painterResource(id = R.drawable.backgroun_btn),
                contentDescription = "null")
            Text(
                style = TextStyle(color = Color(0xff0047B3), fontSize = 14.sp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight(500),
                text = "크리테리아\n그래프"
            )
        }

    }

}