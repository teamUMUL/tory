package inu.thebite.tory.screens.notice.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType

@Composable
fun NoticeItemGraph(
    plusList: List<Float>,
    minusList: List<Float>,
    dateList: List<String>,
    successData : Float
) {
//    val plusList = mutableListOf<Float>()
//    val minusList = mutableListOf<Float>()
//    sto.result.forEachIndexed { index, data ->
//        if (index % 2 == 0) {
//            // If the index is even, add it to plusList
//            plusList.add(data)
//        } else {
//            // If the index is odd, add it to minusList
//            minusList.add(data)
//        }
//    }
//    var stoName = ""
//    stoViewModel.findSTOById(sto.stoId)?.let {
//        stoName = it.name
//    }
//    val stoId = sto.stoId
    if (plusList.isNotEmpty()) {
        val steps = 10
        var plusIndex = 0f
        var minusIndex = 0f
        var limitLineIndex = 0f
        var successLineIndex = 0f
        var minLineIndex = 0f

        val limitLine = mutableListOf<Point>()
        for (a in plusList) {
            limitLine.add(Point(limitLineIndex, 100f))
            limitLineIndex += 1f
        }
        val minLine = mutableListOf<Point>()
        for (a in plusList) {
            minLine.add(Point(minLineIndex, 0f))
            minLineIndex += 1f
        }
        val successLine = mutableListOf<Point>()
        for (b in plusList) {
            successLine.add(Point(successLineIndex, successData))
            successLineIndex += 1f
        }
        val pointsData1 = mutableListOf<Point>()
        for (plus in plusList) {
            pointsData1.add(Point(plusIndex, plus.toInt().toFloat()))
            Log.e("성공값", plus.toInt().toFloat().toString())
            plusIndex += 1f
        }
        val pointsData2 = mutableListOf<Point>()
        for (minus in minusList) {
            pointsData2.add(Point(minusIndex, minus.toInt().toFloat()))
            Log.e("실패값", minus.toInt().toFloat().toString())
            minusIndex += 1f
        }
        val xAxisLabelList = dateList
        val xAxisData = AxisData.Builder()
            .shouldDrawAxisLineTillEnd(true)
            .axisStepSize(40.dp)
            .backgroundColor(Color.Transparent)
            .steps(pointsData1.size - 1)
            .labelData { i ->
                "          " + xAxisLabelList[i]
            }
            .labelAndAxisLinePadding(0.dp)
            .axisLineColor(Color.Black)
            .axisLabelColor(Color.Black)
            .axisLabelAngle(20f)
            .startPadding(10.dp)
            .backgroundColor(Color.White)
            .build()

        val yAxisData = AxisData.Builder()
            .steps(steps)
            .axisStepSize(30.dp)
            .backgroundColor(Color.Transparent)
            .labelData { i ->
//                val yScale = 100 / steps
//                val yLabel = (i * yScale).toString()+"%"
//                yLabel
                " "
            }
            .labelAndAxisLinePadding(30.dp)
            .axisLineColor(Color.Black)
            .axisLabelColor(Color.Black)
//            .startPadding(0.dp)
            .backgroundColor(Color.White)
//            .axisOffset(-20.dp)
            .build()

        val lineChardData = LineChartData(
            linePlotData = LinePlotData(
                plotType = PlotType.Line,
                lines = listOf(
                    Line(
                        dataPoints = pointsData1.toList(),
                        LineStyle(
                            color = Color.Green,
                            lineType = LineType.Straight(isDotted = false),
                            width = 6f
                        ),
                        IntersectionPoint(
                            color = Color.Green,
                            radius = 3.dp
                        ),
//                        selectionHighlightPopUp = SelectionHighlightPopUp(
//                            popUpLabel = {plusX ,plusY ->
//                                "$plusX, $plusY"
//                            }
//                        )
                    ),
                    Line(
                        dataPoints = pointsData2.toList(),
                        LineStyle(
                            color = Color.Red,
                            lineType = LineType.Straight(isDotted = true),
                            width = 6f
                        ),
                        IntersectionPoint(
                            color = Color.Red,
                            radius = 3.dp
                        ),
//                        selectionHighlightPopUp = SelectionHighlightPopUp(
//                            popUpLabel = {minusX ,minusY ->
//                                "$minusX, $minusY"
//                            }
//                        ),

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
//                        SelectionHighlightPoint(color = Color.Transparent),
                    ),
                    Line(
                        dataPoints = minLine.toList(),
                        LineStyle(
                            color = Color.Transparent,
                            lineType = LineType.Straight(isDotted = false)
                        ),
//                        IntersectionPoint(
//                            color = Color.Transparent
//                        ),
//                        SelectionHighlightPoint(color = Color.Transparent),
                    ),
                    Line(
                        dataPoints = successLine.toList(),
                        LineStyle(
                            color = Color.Red.copy(0.2f),
                            lineType = LineType.Straight(isDotted = true)
                        ),
//                        IntersectionPoint(
//                            color = Color.Transparent
//                        ),
//                        SelectionHighlightPoint(color = Color.Transparent),
                    )

                )
            ),
            backgroundColor = Color.Transparent,
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            bottomPadding = 20.dp,
            paddingRight = 0.dp,
            gridLines = GridLines(
                Color.LightGray,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            ),
            paddingTop = 10.dp,
            isZoomAllowed = false
        )
        Card(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                LineChart(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    lineChartData = lineChardData
                )
            }

        }
    }
}