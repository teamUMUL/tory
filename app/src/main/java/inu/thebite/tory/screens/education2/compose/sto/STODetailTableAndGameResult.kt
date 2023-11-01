package inu.thebite.tory.screens.education2.compose.sto

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.database.education.EducationEntity
import inu.thebite.tory.model.point.AddPointRequest
import inu.thebite.tory.model.point.DeletePointRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.UpdateStoRequest
import inu.thebite.tory.screens.education2.viewmodel.EducationViewModel
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel
import kotlinx.coroutines.delay

@SuppressLint("SimpleDateFormat")
@Composable
fun STODetailTableAndGameResult(
    selectedSTO: StoResponse,
    points: List<String>,
    stoViewModel: STOViewModel,
    setSelectedSTOStatus : (String) -> Unit
){
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 0.dp,
                bottom = 0.dp,
                top = 0.dp,
                end = 0.dp
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(500.dp)
                .padding(10.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
        ) {
            if(selectedSTO.isNotNull()){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    items(STODetailTitles) { stoDetailItem ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                        ) {
                            val selectedSTODetail = listOf(
                                selectedSTO.name,
                                selectedSTO.contents,
                                "${selectedSTO.count}회",
                                "${selectedSTO.goalPercent}%",
                                selectedSTO.urgeContent,
                                selectedSTO.enforceContent,
                                selectedSTO.memo,
                            )
                            TableCell(text = stoDetailItem, weight = 0.3f)

                            TableCellWithLeftLine(
                                text = selectedSTODetail[STODetailTitles.indexOf(stoDetailItem)], weight = 0.7f
                            )

                        }
                        Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

        }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(start = 0.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.1f)
                            .padding(10.dp)
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(8.dp)
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "정반응 :", modifier = Modifier.padding(horizontal = 5.dp))
                            Text(
                                text = points.count { it == "+" }.toString(),
                                modifier = Modifier.padding(horizontal = 5.dp)
                            )
                            Divider(
                                color = MaterialTheme.colorScheme.primary, modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                            )
                            Text(text = "촉구 :", modifier = Modifier.padding(horizontal = 5.dp))
                            Text(
                                text = points.count { it == "P" }.toString(),
                                modifier = Modifier.padding(horizontal = 5.dp)
                            )

                            Divider(
                                color = MaterialTheme.colorScheme.primary, modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                            )
                            Text(text = "미반응 :", modifier = Modifier.padding(horizontal = 5.dp))
                            Text(
                                text = points.count { it == "-" }.toString(),
                                modifier = Modifier.padding(horizontal = 5.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "회차 :", modifier = Modifier.padding(horizontal = 5.dp))
                            Text(
                                text = selectedSTO.round.toString(),
                                modifier = Modifier
                                    .padding(horizontal = 5.dp))
                        }
                    }
                    Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.8f)
                            .padding(10.dp)
                    ) {
                        items(selectedSTO.count / 5) { verticalIndex ->
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(2.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                items(5) { horizonIndex ->
                                    var stoGameData = ""
                                    if(points.size > (5 * verticalIndex) + horizonIndex){
                                        stoGameData = points[(5 * verticalIndex) + horizonIndex]
                                    } else {
                                        stoGameData = "n"
                                    }
                                    Card(
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .width(65.dp)
                                            .fillMaxHeight(0.3f),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor =
                                                when (stoGameData) {
                                                    "+" -> {
                                                        Color.Green.copy(alpha = 0.55f)
                                                    }

                                                    "-" -> {
                                                        Color.Red.copy(alpha = 0.55f)
                                                    }

                                                    "P" -> {
                                                        Color.Yellow.copy(alpha = 0.55f)
                                                    }

                                                    else -> {
                                                        Color.Gray.copy(alpha = 0.55f)
                                                    }
                                                }

                                        ),
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = if (stoGameData != "n") stoGameData else "",
                                                fontSize = 24.sp,
                                                color = Color.White,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        item {
                            if(points.size == selectedSTO.count) {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .padding(vertical = 5.dp),
                                    onClick = {
                                        //+비율이 90%이상인 경우 자동으로 준거완료 설정
                                        var status = "준비중"
                                        if((points.count { it == "+" }.toFloat()/selectedSTO.count.toFloat())*100 >= 90f){
                                            status = "준거 도달"
                                        }else{
                                            status = "진행중"
                                            //--------------
                                        }
                                        val plusRate = (points.count { it == "+" }.toFloat()/selectedSTO.count.toFloat())*100
                                        val minusRate = (points.count { it == "-" }.toFloat()/selectedSTO.count.toFloat())*100
                                        stoViewModel.addRound(
                                            selectedSTO = selectedSTO,
                                            registrant = "테스트",
                                            plusRate = plusRate,
                                            minusRate = minusRate,
                                            status = status
                                        )

//                                        selectedSTODetailGameDataIndex.intValue = 0

//
//                                        selectedSTO. += (selectedSTO.gameResult.count { it == "-" }.toFloat()/selectedSTO.gameResult.size.toFloat())*100
//                                        selectedSTO.plusRatio += (selectedSTO.gameResult.count { it == "+" }.toFloat()/selectedSTO.gameResult.size.toFloat())*100
//                                        val formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
//                                        val date = SimpleDateFormat("yyyy-MM-dd").parse(formattedDate)
//                                        selectedSTO.date += (date)
//                                        selectedSTO.gameResult = List(selectedSTO.gameResult.size){"n"}
//
//                                        stoViewModel.updateSTO(selectedSTO)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Cyan.copy(alpha = 0.4f)
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "회차 추가",
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                    }

                    Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)

                    LazyRow(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val buttonList = listOf<String>("+", "-", "P", "삭제")
                        items(buttonList) { buttonItem ->
                            OutlinedButton(
                                modifier = Modifier
                                    .width(85.dp)
                                    .fillMaxHeight()
                                    .padding(vertical = 5.dp, horizontal = 2.dp),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(
                                    width = 2.dp, color =
                                    when (buttonItem) {
                                        "+" -> {
                                            Color.Green.copy(alpha = 0.95f)
                                        }

                                        "-" -> {
                                            Color.Red.copy(alpha = 0.95f)
                                        }

                                        "P" -> {
                                            Color.Yellow.copy(alpha = 0.95f)
                                        }

                                        else -> {
                                            Color.LightGray.copy(alpha = 0.95f)
                                        }
                                    }
                                ),
                                onClick = {
                                    if(buttonItem == "삭제"){
                                        stoViewModel.deletePoint(selectedSTO = selectedSTO, deletePointRequest = DeletePointRequest(registrant = "테스트"))
                                    }
                                    if(points.size < selectedSTO.count){
                                        when (buttonItem) {
                                            "+" -> {
                                                stoViewModel.addPoint(selectedSTO = selectedSTO, addPointRequest = AddPointRequest(result = "+", registrant = "테스트"))
                                            }
                                            "-" -> {
                                                stoViewModel.addPoint(selectedSTO = selectedSTO, addPointRequest = AddPointRequest(result = "-", registrant = "테스트"))
                                            }
                                            "P" -> {
                                                stoViewModel.addPoint(selectedSTO = selectedSTO, addPointRequest = AddPointRequest(result = "P", registrant = "테스트"))
                                            }
                                            else -> {
                                                stoViewModel.deletePoint(selectedSTO = selectedSTO, deletePointRequest = DeletePointRequest(registrant = "테스트"))
                                            }
                                        }
                                    }

                                },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(
                                    modifier = Modifier.padding(16.dp),
                                    text = when (buttonItem) {
                                        "+" -> {
                                            "+"
                                        }

                                        "-" -> {
                                            "-"
                                        }

                                        "P" -> {
                                            "P"
                                        }

                                        else -> {
                                            "삭제"
                                        }
                                    },
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.SemiBold,

                                    )
                            }
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
        Modifier
            .weight(weight)
            .padding(8.dp)
    )

}

@Composable
fun RowScope.TableCellWithLeftLine(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .weight(weight)
            .padding(8.dp)
            .drawBehind {
                val strokeWidth = 4f
                val x = size.width - strokeWidth
                val y = size.height - strokeWidth
                //left line
                drawLine(
                    color = Color(0xFF888888),
                    start = Offset(0f - 10f, 0f - 20f), //(0,0) at top-left point of the box
                    end = Offset(0f - 10f, y + 22f),//bottom-left point of the box
                    strokeWidth = strokeWidth
                )
            }
    )

}