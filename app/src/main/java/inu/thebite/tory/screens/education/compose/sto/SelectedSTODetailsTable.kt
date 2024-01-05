package inu.thebite.tory.screens.education.compose.sto

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato


@Composable
fun SelectedSTODetailsTable(
    modifier: Modifier = Modifier,
    selectedSTO: StoResponse?
) {
    val STODetailTitles =
        listOf<String>(
            "STO 이름",
            "STO 내용",
            "시도 수",
            "준거도달 기준",
            "촉구방법",
            "강화스케줄",
            "메모",
        )
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "STO Details",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = fontFamily_Lato,
                fontWeight = FontWeight(900),
                color = Color(0xFF1D1C1D),
            ),
            modifier = Modifier.padding(start = 10.dp, top = 30.dp)
        )
        Row(
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .border(width = 2.dp, color = Color(0x4D000000), shape = RectangleShape)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(STODetailTitles) { stoDetailItem ->
                    Row(
                        Modifier
                            .fillMaxWidth()
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
                        TableCell(text = stoDetailItem, weight = 0.3f)

                        TableCellWithLeftLine(
                            text = selectedSTODetail[STODetailTitles.indexOf(stoDetailItem)],
                            weight = 0.7f
                        )

                    }
                    Divider(thickness = 1.dp, color = Color(0x4F000000))
                }
            }

        }
    }

}


@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = fontFamily_Inter,
            fontWeight = FontWeight(500),
            color = Color(0xFF0047B3),

            textAlign = TextAlign.Center,
        ),
        modifier = Modifier
            .weight(weight)
            .padding(horizontal = 8.dp, vertical = 20.dp),

        )

}

@Composable
fun RowScope.TableCellWithLeftLine(
    text: String,
    weight: Float
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
            .padding(horizontal = 8.dp, vertical = 20.dp)
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