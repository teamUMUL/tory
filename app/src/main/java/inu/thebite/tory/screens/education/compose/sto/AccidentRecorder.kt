package inu.thebite.tory.screens.education.compose.sto

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.annotations.SerializedName
import inu.thebite.tory.R
import inu.thebite.tory.ui.theme.fontFamily_Lato

@Composable
fun AccidentRecorder(

){
    Row(
        modifier = Modifier
            .fillMaxSize()
            .border(width = 1.dp, color = Color(0xFF0047B3).copy(alpha = 0.5f), shape = RoundedCornerShape(10))
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(5f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f),
                verticalArrangement = Arrangement.Center
            ) {
                DottedLine()
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = Color(0xFFA3A3A3)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_record_start),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(50.dp))
                Icon(
                    painter = painterResource(id = R.drawable.icon_record_recording),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(50.dp))
                Icon(
                    painter = painterResource(id = R.drawable.icon_record_pause),
                    contentDescription = null
                )
            }

        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
            color = Color(0xFFA3A3A3)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(5f)
        ) {
            val dummyRecordList = listOf(
                RecordResponse(
                    id = 1L,
                    name = "숟가락, 양말, 칫솔",
                    time = "오후 1:43",
                    length = "00:46"
                ),
                RecordResponse(
                    id = 2L,
                    name = "숟가락, 곰돌이, 컵",
                    time = "오후 1:37",
                    length = "01:01"
                ),
                RecordResponse(
                    id = 3L,
                    name = "축구공, 가위, 곰돌이",
                    time = "오후 1:24",
                    length = "01:31"
                ),
                RecordResponse(
                    id = 4L,
                    name = "블록, 양말, 컵",
                    time = "오후 1:21",
                    length = "00:03"
                )
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ){
                items(dummyRecordList){recordItem ->
                   Column(
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(40.dp),
                       verticalArrangement = Arrangement.Center
                   ) {
                       Text(
                           text = recordItem.name,
                           style = TextStyle(
                               fontFamily = fontFamily_Lato,
                               fontSize = 12.sp,
                               color = Color(0xFF1D1C1D)
                           )
                       )
                       Row(
                           modifier = Modifier
                               .fillMaxWidth(),
                           horizontalArrangement = Arrangement.SpaceBetween
                       ) {
                           Text(
                               text = recordItem.time,
                               style = TextStyle(
                                   fontFamily = fontFamily_Lato,
                                   fontSize = 8.sp,
                                   color = Color(0xFF1D1C1D)
                               )
                           )
                           Text(
                               text = recordItem.length,
                               style = TextStyle(
                                   fontFamily = fontFamily_Lato,
                                   fontSize = 8.sp,
                                   color = Color(0xFF1D1C1D)
                               )
                           )
                       }
                   }
                    DottedLine()
                }
            }

        }
    }
}

data class RecordResponse(

    @SerializedName("id")
    var id: Long,

    @SerializedName("name")
    var name: String,

    @SerializedName("time")
    var time: String,

    @SerializedName("length")
    var length: String


)

@Composable
fun DottedLine(
    color: Color = Color(0xFFA3A3A3),
    strokeWidth: Float = 2f, // 선의 두께
    dashLength: Float = 5f, // 점선의 실제 부분 길이
    gapLength: Float = 5f // 점선 사이의 간격
) {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = strokeWidth,
            pathEffect = pathEffect
        )
    }
}