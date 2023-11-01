package inu.thebite.tory.screens.education2.compose.graph

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import inu.thebite.tory.model.lto.LtoGraphResponse
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel

@Composable
fun GraphRow(
    stos: List<LtoGraphResponse>,
    stoViewModel: STOViewModel,
){
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .border(4.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
    ) {
            items(stos){ sto ->
                var stoName = ""
                stoViewModel.findSTOById(sto.stoId)?.let {
                    stoName = it.name
                }
                val stoId = sto.stoId
                if(sto.result[0].isNotEmpty()){
                    val steps = 5
                    var plusIndex = 0f
                    var minusIndex = 0f
                    var limitLineIndex = 0f
                    var successLineIndex = 0f
                    var minLineIndex = 0f

                    val limitLine = mutableListOf<Point>()
                    for(a in sto.result[0]){
                        limitLine.add(Point(limitLineIndex, 100f))
                        limitLineIndex += 1f
                    }
                    val minLine = mutableListOf<Point>()
                    for(a in sto.result[0]){
                        minLine.add(Point(minLineIndex, 0f))
                        minLineIndex += 1f
                    }
                    val successLine = mutableListOf<Point>()
                    for(b in sto.result[0]){
                        successLine.add(Point(successLineIndex, 90f))
                        successLineIndex += 1f
                    }
                    val pointsData1 = mutableListOf<Point>()
                    for(plus in sto.result[0]){
                        pointsData1.add(Point(plusIndex, plus.toInt().toFloat()))
                        Log.e("성공값", plus.toInt().toFloat().toString())
                        plusIndex += 1f
                    }
                    val pointsData2 = mutableListOf<Point>()
                    for(minus in sto.result[1]){
                        pointsData2.add(Point(minusIndex, minus.toInt().toFloat()))
                        Log.e("실패값", minus.toInt().toFloat().toString())
                        minusIndex += 1f
                    }
//                    val xAxisLabelList = sto.date
                    val xAxisData = AxisData.Builder()
                        .axisStepSize(50.dp)
                        .backgroundColor(Color.Transparent)
                        .steps(pointsData1.size - 1)
                        .labelData { i -> "          "/*+formatDateToYYYYMMDD(xAxisLabelList[i]).takeLast(5)*/
                        }
                        .labelAndAxisLinePadding(0.dp)
                        .axisLineColor(Color.Black)
                        .axisLabelColor(Color.Black)
                        .shouldDrawAxisLineTillEnd(true)
                        .axisLabelAngle(20f)
                        .startPadding(10.dp)
                        .build()

                    val yAxisData = AxisData.Builder()
                        .steps(steps)
                        .axisStepSize(50.dp)
                        .backgroundColor(Color.Transparent)
                        .labelData { i ->
                            val yScale = 100 / steps
                            val yLabel = (i * yScale).toString()+"%"
                            yLabel
                        }
                        .labelAndAxisLinePadding(30.dp)
                        .axisLineColor(Color.Black)
                        .axisLabelColor(Color.Black)
                        .startPadding(10.dp)
                        .build()

                    val lineChardData = LineChartData(
                        linePlotData = LinePlotData(
                            lines = listOf(
                                Line(
                                    dataPoints = pointsData1.toList(),
                                    LineStyle(
                                        color = Color.Green,
                                        lineType = LineType.Straight(isDotted = false)
                                    ),
                                    IntersectionPoint(
                                        color = Color.Green
                                    ),
                                    SelectionHighlightPoint(color = Color.Green),
                                    selectionHighlightPopUp = SelectionHighlightPopUp()
                                ),
                                Line(
                                    dataPoints = pointsData2.toList(),
                                    LineStyle(
                                        color = Color.Red,
                                        lineType = LineType.Straight(isDotted = true)
                                    ),
                                    IntersectionPoint(
                                        color = Color.Red
                                    ),
                                    SelectionHighlightPoint(color = Color.Yellow),
                                    selectionHighlightPopUp = SelectionHighlightPopUp()
                                ),
                                Line(
                                    dataPoints = limitLine.toList(),
                                    LineStyle(
                                        color = Color.Transparent,
                                        lineType = LineType.Straight(isDotted = false)
                                    ),
                                    IntersectionPoint(
                                        color = Color.Transparent
                                    ),
                                    SelectionHighlightPoint(color = Color.Transparent),
                                ),
                                Line(
                                    dataPoints = minLine.toList(),
                                    LineStyle(
                                        color = Color.Transparent,
                                        lineType = LineType.Straight(isDotted = false)
                                    ),
                                    IntersectionPoint(
                                        color = Color.Transparent
                                    ),
                                    SelectionHighlightPoint(color = Color.Transparent),
                                ),
                                Line(
                                    dataPoints = successLine.toList(),
                                    LineStyle(
                                        color = Color.Red.copy(0.2f),
                                        lineType = LineType.Straight(isDotted = true)
                                    ),
                                    IntersectionPoint(
                                        color = Color.Transparent
                                    ),
                                    SelectionHighlightPoint(color = Color.Transparent),
                                )

                            )
                        ),
                        backgroundColor = Color.Transparent,
                        xAxisData = xAxisData,
                        yAxisData = yAxisData,
                        bottomPadding = 40.dp,
                        gridLines = GridLines(Color.LightGray),
                        isZoomAllowed = false
                    )
                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(400.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp)
                        ){
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.1f)
                                    .border(
                                        4.dp,
                                        MaterialTheme.colorScheme.secondary,
                                        RoundedCornerShape(8.dp)
                                    )
                                ,
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stoName,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            LineChart(
                                modifier = Modifier
                                    .fillMaxHeight(),
                                lineChartData = lineChardData
                            )
                        }

                    }
                }

            }


    }
}