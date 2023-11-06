package inu.thebite.tory.screens.homescreen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun childInfor(
    modifier: Modifier = Modifier
) {

        Row(modifier= modifier
            .shadow(
                elevation = 4.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000)
            )
//            .width(241.dp)
//            .height(440.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
            .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier= Modifier
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                pieChartPreview(modifier = Modifier. weight(1f))
//                Image(modifier = Modifier
//                    .width(180.dp)
//                    .height(180.dp),
//                    painter = painterResource(id = R.drawable.chart_round),
//                    contentDescription ="null",
//                    contentScale = ContentScale.Fit)
                Column(modifier = Modifier. weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top))
                    {
                    Text(
                        text = "이름",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF0047B3)
                        )
                    )
                    Text(
                        text = "김토리",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF000000),

                            )
                    )
                    Text(
                        text = "생년월일",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF0047B3),

                            )
                    )
                    Text(
                        text = "2020-01-10",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF000000),

                            )
                    )
                    Text(
                        text = "프로그램 기간",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF0047B3),
                        )
                    )
                    Text(
                        text = "2023-01-01~2023-01-01",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF000000),
                        )
                    )
                    Text(
                        text = "특징",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF0047B3),
                        )
                    )
                    Text(
                        text = "발달지연",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF000000),
                        )
                    )
                }
            }
        }

}
@Composable
fun reportList(){
    Row (
        Modifier
            .padding(top = 4.dp)
            .fillMaxWidth()
            .height(78.dp)
    ){
        Box(modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center){
            Image(modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .clickable { }
                .height(70.dp)
                .background(color = Color.Transparent, shape = RoundedCornerShape(size = 10.dp)),painter = painterResource(id = R.drawable.backgroun_btn), contentDescription = "null")
            Text(style = TextStyle(color = Color(0xFF0047B3), fontSize = 20.sp), fontWeight = FontWeight(400),text = "상담일지")
        }
        Spacer(modifier = Modifier.width(15.dp))
        Box(modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center){
            Image(modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .height(70.dp)
                .clickable { }
                .background(color = Color.Transparent, shape = RoundedCornerShape(size = 10.dp)),painter = painterResource(id = R.drawable.backgroun_btn), contentDescription = "null")
            Text(style = TextStyle(color = Color(0xFF0047B3), fontSize = 20.sp), fontWeight = FontWeight(400),text = "아동영상")
        }
        Spacer(modifier = Modifier.width(15.dp))
        Box(modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center){
            Image(modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .height(70.dp)
                .clickable { }
                .background(color = Color.Transparent, shape = RoundedCornerShape(size = 10.dp)),painter = painterResource(id = R.drawable.backgroun_btn), contentDescription = "null")
            Text(style = TextStyle(color = Color(0xFF0047B3), fontSize = 20.sp), fontWeight = FontWeight(400),text = "완료목록")
        }

        Spacer(modifier = Modifier.width(15.dp))
        Box(modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center){
            Image(modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .height(70.dp)
                .clickable { }
                .background(color = Color.Transparent, shape = RoundedCornerShape(size = 10.dp)),painter = painterResource(id = R.drawable.backgroun_btn), contentDescription = "null")
            Text(style = TextStyle(color = Color(0xFF0047B3), fontSize = 20.sp), fontWeight = FontWeight(400),text = "그래프")
        }
        Spacer(modifier = Modifier.width(15.dp))
        Box(modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center){
            Image(modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .height(70.dp)
                .clickable { }
                .background(color = Color.Transparent, shape = RoundedCornerShape(size = 10.dp)),painter = painterResource(id = R.drawable.backgroun_btn), contentDescription = "null")
            Text(style = TextStyle(color = Color(0xff0047B3), fontSize = 14.sp), textAlign = TextAlign.Center, fontWeight = FontWeight(500),text = "크리테리아\n그래프")
        }

    }

}