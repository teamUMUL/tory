package inu.thebite.tory.screens.teachingboard.recentlto.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.ui.theme.fontFamily_Inter


@Composable
fun RecentLTOTopBar(
    modifier: Modifier = Modifier,
    ltoName: String
){
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF0047B3), Color(0xFF7F5AF0))
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 20.dp, end = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "LTO [${ltoName}]",
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 24.sp,
                    fontFamily = fontFamily_Inter,
                    fontWeight = FontWeight(600),
                    brush = gradient,
                    letterSpacing = 0.24.sp,
                )
            )

        }
        Row(
            modifier = Modifier
                .background(
                    gradient,
                    RoundedCornerShape(
                        topEnd = 0.dp,
                        topStart = 0.dp,
                        bottomEnd = 8.dp,
                        bottomStart = 8.dp
                    )
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "영역별 발달지표",
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 24.sp,
                    fontFamily = fontFamily_Inter,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    letterSpacing = 0.24.sp,
                ),
                modifier = Modifier
                    .padding(horizontal = 50.dp, vertical = 7.dp)
            )
        }

    }
}