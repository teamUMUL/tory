package inu.thebite.tory.screens.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun childInfor(){
    Box(
        modifier = Modifier
            .width(309.dp)
            .height(426.dp)
            .padding(12.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 20.dp))
    ) {
        Column(modifier = Modifier
            .padding(start = 24.dp, top = 4.dp)



        ) {
            Text(
                text = "이름",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF0E4B32),
                )
            )
            Spacer(modifier = Modifier.size(23.dp))
            Text(
                text = "김토리",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),

                    )
            )
            Spacer(modifier = Modifier.size(23.dp))
            Text(
                text = "생년월일",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF0E4B32),

                    )
            )
            Spacer(modifier = Modifier.size(23.dp))
            Text(
                text = "2020-01-10",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),

                    )
            )
            Spacer(modifier = Modifier.size(23.dp))
            Text(
                text = "프로그램 기간",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF0E4B32),
                )
            )
            Spacer(modifier = Modifier.size(23.dp))
            Text(
                text = "2023-01-01~2023-01-01",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),
                )
            )
            Spacer(modifier = Modifier.size(23.dp))
            Text(
                text = "특징",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF0E4B32),
                )
            )
            Spacer(modifier = Modifier.size(23.dp))
            Text(
                text = "발달지연",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),
                )
            )
        }
    }
}
@Composable
fun editProgram(){
    Box(modifier = Modifier
        .width(309.dp)
        .height(426.dp)
        .padding(12.dp)
        .background(color = Color(0xFF59B791), shape = RoundedCornerShape(size = 20.dp))
        .clickable {  },
        contentAlignment = Alignment.Center
    ) {
        Text(modifier = Modifier
            .background(color = Color(0xFF59B791)),
            text = "프로그램 편집\n&데일리",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            )
        )

    }
}

@Composable
fun reportList(){
    Column (modifier = Modifier
        .padding(start = 12.dp, top = 16.dp)
        .width(132.dp)
        .height(405.dp)

    ){
        Box(modifier = Modifier
            .width(131.dp)
            .height(124.dp)
            .padding(8.dp)
            .background(color = Color(0xFF13A694), shape = RoundedCornerShape(size = 20.dp))
        ){
            Text(modifier = Modifier
                .width(67.dp)
                .height(22.dp)
                .align(Alignment.Center),
                text = "상담일지",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),

                    )
            )
        }
        Box(modifier = Modifier
            .width(131.dp)
            .height(124.dp)
            .padding(8.dp)
            .background(color = Color(0xFF13A694), shape = RoundedCornerShape(size = 20.dp))
        ){
            Text(modifier = Modifier
                .width(67.dp)
                .height(22.dp)
                .align(Alignment.Center),
                text = "아동영상",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),

                    )
            )
        }
        Box(modifier = Modifier
            .width(131.dp)
            .height(124.dp)
            .padding(8.dp)
            .background(color = Color(0xFF636363), shape = RoundedCornerShape(size = 20.dp))
        ){
            Text(modifier = Modifier
                .width(50.dp)
                .height(22.dp)
                .align(Alignment.Center),
                text = "보고서",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),

                    )
            )
        }

    }
    Column (modifier = Modifier
        .padding(start = 12.dp, top = 16.dp)
        .width(132.dp)
        .height(405.dp)

    ){
        Box(modifier = Modifier
            .width(131.dp)
            .height(124.dp)
            .padding(8.dp)
            .background(color = Color(0xFF00CD6B), shape = RoundedCornerShape(size = 20.dp))
        ){
            Text(modifier = Modifier
                .width(67.dp)
                .height(22.dp)
                .align(Alignment.Center),
                text = "완료목록",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),

                    )
            )
        }
        Box(modifier = Modifier
            .width(131.dp)
            .height(124.dp)
            .padding(8.dp)
            .background(color = Color(0xFF636363), shape = RoundedCornerShape(size = 20.dp))
        ){
            Text(modifier = Modifier
                .width(50.dp)
                .height(22.dp)
                .align(Alignment.Center),
                text = "그래프",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),

                    )
            )
        }
        Box(modifier = Modifier
            .width(131.dp)
            .height(124.dp)
            .padding(8.dp)
            .background(color = Color(0xFF636363), shape = RoundedCornerShape(size = 20.dp))
        ){
            Text(modifier = Modifier
                .width(83.dp)
                .height(44.dp)
                .align(Alignment.Center),
                text = "크리테리아\n그래프",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center

                )
            )
        }

    }
}