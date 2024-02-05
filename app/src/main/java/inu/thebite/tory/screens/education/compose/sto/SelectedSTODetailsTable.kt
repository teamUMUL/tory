package inu.thebite.tory.screens.education.compose.sto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedSTODetailsTable(
    modifier: Modifier = Modifier,
    selectedSTO: StoResponse?
) {
    val STODetailTitles =
        listOf<String>(
            "이름",
            "내용",
            "시도 수",
            "준거 도달 기준",
            "촉구방법",
            "강화 메세지",
        )
    var stoSignificant by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "STO Details",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = fontFamily_Lato,
                fontWeight = FontWeight(900),
                color = Color(0xFF1D1C1D),
            ),
            modifier = Modifier.padding(start = 10.dp, top = 30.dp)
        )
        Column(
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth()
                .weight(4.5f)
//                .border(width = 2.dp, color = Color(0x4D000000), shape = RectangleShape)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 2.dp, color = Color(0x4D000000), shape = RectangleShape)

            ) {
                items(STODetailTitles) { stoDetailItem ->
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val selectedSTODetail = listOf(
                            selectedSTO?.name ?: "",
                            selectedSTO?.contents ?: "",
                            "${selectedSTO?.count ?: ""}회",
                            "${selectedSTO?.goal ?: ""}%",
                            selectedSTO?.urgeContent ?: "",
                            selectedSTO?.enforceContent ?: "",
                            selectedSTO?.memo ?: "",
                        )
                        TableCell(
                            text = stoDetailItem,
                            weight = 0.3f,
                            isBigger = stoDetailItem == "내용"
                        )

                        TableCellWithLeftLine(
                            text = selectedSTODetail[STODetailTitles.indexOf(stoDetailItem)],
                            weight = 0.7f,
                            isBigger = stoDetailItem == "내용"
                        )

                    }
                    Divider(thickness = 1.dp, color = Color(0x4F000000))
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5.5f)
        ) {
            val accidentButtonList = listOf(
                "고함", "장난", "폭력", "회피", "자해", "말 끊기", "물건 던지기", "반복 행동", "무발언", "반복 발언", "거부"
            )
            val stressState = listOf(
                "매우 나쁨", "나쁨", "보통", "좋음", "매우 좋음"
            )
            val focusState = listOf(
                "매우 나쁨", "나쁨", "보통", "좋음", "매우 좋음"
            )
            Text(
                text = "돌발행동",
                style = TextStyle(
                    fontFamily = fontFamily_Lato,
                    color = Color(0xFF1D1C1D),
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(start = 10.dp)
            )
            LazyHorizontalGrid(
                rows = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .padding(10.dp)
            ) {
                items(accidentButtonList) { accidentButtonItem ->
                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp),
                        border = BorderStroke(width = 1.dp, color = Color(0xFF0047B3)),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = accidentButtonItem)
                    }
                }
            }
            Text(
                text = "스트레스 상태",
                style = TextStyle(
                    fontFamily = fontFamily_Lato,
                    color = Color(0xFF1D1C1D),
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(start = 10.dp)
            )
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.25f)
                    .padding(10.dp)
            ) {
                items(stressState) { stressStateItem ->
                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp),
                        border = BorderStroke(width = 1.dp, color = Color(0xFF0047B3)),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = stressStateItem)
                    }
                }
            }
            Text(
                text = "집중도",
                style = TextStyle(
                    fontFamily = fontFamily_Lato,
                    color = Color(0xFF1D1C1D),
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(start = 10.dp)
            )
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.25f)
                    .padding(10.dp)
            ) {
                items(focusState) { focusStateItem ->
                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp),
                        border = BorderStroke(width = 1.dp, color = Color(0xFF0047B3)),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = focusStateItem)
                    }
                }
            }
            Text(
                text = "특이사항",
                style = TextStyle(
                    fontFamily = fontFamily_Lato,
                    color = Color(0xFF1D1C1D),
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(start = 10.dp)
            )
            TextField(
                value = stoSignificant,
                onValueChange = {
                    stoSignificant = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.5f)
                    .padding(10.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF0047B3),
                        shape = RoundedCornerShape(10.dp)
                    ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                )
            )

        }
    }

}


@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    isBigger: Boolean = false
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = fontFamily_Inter,
            fontWeight = FontWeight(500),
            color = Color.Black,

            textAlign = TextAlign.Center,
        ),
        modifier = Modifier
            .weight(weight)
            .padding(horizontal = 8.dp, vertical = if (isBigger) 30.dp else 15.dp),

        )

}

@Composable
fun RowScope.TableCellWithLeftLine(
    text: String,
    weight: Float,
    isBigger: Boolean = false
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = fontFamily_Inter,
            fontWeight = FontWeight(500),
            color = Color(0xFF000000),

            textAlign = TextAlign.Center,
        ),
        modifier = Modifier
            .weight(weight)
            .padding(horizontal = 8.dp, vertical = if (isBigger) 30.dp else 15.dp)
            .drawBehind {
                val strokeWidth = 2f
                val x = size.width - strokeWidth
                val y = size.height - strokeWidth
                //left line
                drawLine(
                    color = Color(0xFF888888),
                    start = Offset(0f - 10f, 0f - 40f), //(0,0) at top-left point of the box
                    end = Offset(0f - 10f, y + 40f),//bottom-left point of the box
                    strokeWidth = strokeWidth
                )
            }
    )

}