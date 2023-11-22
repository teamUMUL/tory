package inu.thebite.tory.screens.notice

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import es.dmoral.toasty.Toasty
import inu.thebite.tory.R
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.ui.theme.fontFamily_Inter

@Composable
fun NoticeScreen(

) {
    var selectedDate by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .weight(1.5f)
                .fillMaxHeight()
        ) {
            NoticeDateColumn(
                selectedDate = selectedDate,
                setSelectedDate = { selectedDate = it }
            )
        }
        Row(
            modifier = Modifier
                .weight(8.5f)
                .fillMaxHeight()
        ) {
            if (selectedDate.isNotEmpty()) {
                NoticeInfoColumn(selectedDate = selectedDate)
            }
        }
    }

}

@Composable
fun NoticeDateColumn(
    selectedDate: String,
    setSelectedDate: (String) -> Unit
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF0047B3), Color(0xFF7F5AF0))
    )
    val dummyDateList = mutableListOf<String>()
    for (i in 1..30) {
        dummyDateList.add("2023/11/${i} (월)")
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = gradient,
            )
    ) {
        items(dummyDateList) { date ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { setSelectedDate(date) }
                    .background(
                        if (selectedDate == date) Color.Black.copy(alpha = 0.4f) else Color.Transparent
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = date,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFFFFFF),
                    ),
                    modifier = Modifier
                        .padding(vertical = 25.dp, horizontal = 20.dp)
                )
                if (selectedDate == date) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_export),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(end = 20.dp)
                    )
                }

            }


        }
    }
}

@Composable
fun NoticeInfoColumn(
    selectedDate: String,
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF0047B3), Color(0xFF7F5AF0))
    )


    val dummySTOList = mutableListOf<StoResponse>().toMutableStateList()
    for (i in 1..8) {
        dummySTOList.add(
            StoResponse(
                id = i.toLong(),
                templateNum = i,
                status = "진행중",
                name = "같은 사진 매칭(${i} array)",
                contents = "",
                count = i,
                goal = i,
                goalPercent = i,
                achievementOrNot = "",
                urgeType = "",
                urgeContent = "",
                enforceContent = "",
                memo = "",
                hitGoalDate = "",
                registerDate = "",
                delYN = "",
                round = i,
                imageList = listOf(),
                pointList = listOf(),
                lto = LtoResponse(
                    id = i.toLong(),
                    templateNum = i,
                    status = "",
                    name = "더미용 LTO ${i}",
                    contents = "",
                    game = "",
                    achieveDate = "",
                    registerDate = "",
                    delYN = "",
                    domain = DomainResponse(
                        id = i.toLong(),
                        templateNum = i,
                        type = "",
                        status = "",
                        name = "더미용 Domain ${i}",
                        contents = "",
                        useYN = "",
                        delYN = "",
                        registerDate = ""
                    )
                )
            )
        )
    }
    val uniqueLTOList = dummySTOList.map { it.lto }.distinctBy { it.id }
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .padding(horizontal = 25.dp, vertical = 8.dp)
                    .shadow(
                        elevation = 16.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000),
                        clip = false
                    )
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(color = Color(0xFFE7EBF0), shape = RoundedCornerShape(size = 10.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2.7f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .fillMaxHeight()
                            .shadow(
                                elevation = 4.dp,
                                spotColor = Color(0x40000000),
                                ambientColor = Color(0x40000000),
                                clip = false
                            )
                            .background(
                                color = Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(size = 10.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = gradient,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .fillMaxHeight()
                                .weight(0.22f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = extractDate(selectedDate),
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    lineHeight = 24.sp,
                                    fontFamily = fontFamily_Inter,
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFFFFFFFF),
                                    letterSpacing = 0.24.sp,
                                )

                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(0.56f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "오늘의 총평",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    lineHeight = 24.sp,
                                    fontFamily = fontFamily_Inter,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF000000),

                                    letterSpacing = 0.24.sp,
                                )
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(0.22f),
                            contentAlignment = Alignment.Center
                        ) {
//                            IconButton(
//                                modifier = Modifier
//                                    .alpha(ContentAlpha.medium)
//                                    .rotate(rotationState),
//                                onClick = {
//                                    expandedState = !expandedState
//                                }) {
//                                Icon(
//                                    imageVector = Icons.Default.ArrowDropDown,
//                                    contentDescription = "Drop-Down Arrow",
//                                    modifier = Modifier
//                                        .graphicsLayer(alpha = 0.99f)
//                                        .drawWithCache {
//                                            onDrawWithContent {
//                                                drawContent()
//                                                drawRect(gradient, blendMode = BlendMode.SrcAtop)
//                                            }
//                                        }
//                                        .size(40.dp)
//                                )
//                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(7.3f),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 35.dp, bottom = 30.dp, end = 35.dp, top = 0.dp)
                            .fillMaxSize()
                            .border(
                                width = 2.dp,
                                color = Color(0xFF0047B3),
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                            .background(
                                color = Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                            value = "오늘은 LTO 1, LTO 2를 실시했습니다. \n오늘 실시한 LTO 1, LTO 2와 관련된 교육을 가정에서도 진행해주시면 더욱 효과적입니다. ",
                            onValueChange = {},
                            readOnly = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontFamily = fontFamily_Inter,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF0047B3),
                                letterSpacing = 0.16.sp,
                                textAlign = TextAlign.Start,
                            )
                        )
                    }
                }
            }
        }
        items(uniqueLTOList) { lto ->
            LTOItem(lto = lto)
        }
    }
}

fun extractDate(input: String): String {
    // 연도를 포함한 처음 5자리를 건너뛰고, 그 다음 5자리 (월/일)를 가져옵니다.
    return input.substring(5, 10)
}

@Composable
fun LTOItem(
    lto: LtoResponse,
) {
    val context = LocalContext.current
    val dummySTOList = mutableListOf<StoResponse>().toMutableStateList()
    for (i in 1..8) {
        dummySTOList.add(
            StoResponse(
                id = i.toLong(),
                templateNum = i,
                status = "진행중",
                name = "같은 사진 매칭(${i} array)ssssssssasasasasss",
                contents = "",
                count = i,
                goal = i,
                goalPercent = i,
                achievementOrNot = "",
                urgeType = "",
                urgeContent = "",
                enforceContent = "",
                memo = "",
                hitGoalDate = "",
                registerDate = "",
                delYN = "",
                round = i,
                imageList = listOf(),
                pointList = listOf(),
                lto = LtoResponse(
                    id = i.toLong(),
                    templateNum = i,
                    status = "",
                    name = "더미용 LTO ${i}",
                    contents = "",
                    game = "",
                    achieveDate = "",
                    registerDate = "",
                    delYN = "",
                    domain = DomainResponse(
                        id = i.toLong(),
                        templateNum = i,
                        type = "",
                        status = "",
                        name = "더미용 Domain ${i}",
                        contents = "",
                        useYN = "",
                        delYN = "",
                        registerDate = ""
                    )
                )
            )
        )
    }

    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF0047B3), Color(0xFF7F5AF0))
    )
    var expandedState by rememberSaveable { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )
    Card(
        modifier = Modifier
            .padding(horizontal = 25.dp, vertical = 8.dp)
            .shadow(
                elevation = 16.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000),
                clip = false
            )
            .fillMaxWidth()
            .background(color = Color(0xFFE7EBF0), shape = RoundedCornerShape(size = 10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFE7EBF0), shape = RoundedCornerShape(size = 10.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .fillMaxHeight()
                        .shadow(
                            elevation = 4.dp,
                            spotColor = Color(0x40000000),
                            ambientColor = Color(0x40000000),
                            clip = false
                        )
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 10.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                brush = gradient,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .fillMaxHeight()
                            .weight(0.22f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "LTO",
                            style = TextStyle(
                                fontSize = 24.sp,
                                lineHeight = 24.sp,
                                fontFamily = fontFamily_Inter,
                                fontWeight = FontWeight(600),
                                color = Color(0xFFFFFFFF),
                                letterSpacing = 0.24.sp,
                            )

                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.56f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = lto.name,
                            style = TextStyle(
                                fontSize = 24.sp,
                                lineHeight = 24.sp,
                                fontFamily = fontFamily_Inter,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF000000),

                                letterSpacing = 0.24.sp,
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.22f),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop-Down Arrow",
                            modifier = Modifier
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    expandedState = !expandedState
                                }
                                .rotate(rotationState)
                                .graphicsLayer(alpha = 0.99f)
                                .drawWithCache {
                                    onDrawWithContent {
                                        drawContent()
                                        drawRect(gradient, blendMode = BlendMode.SrcAtop)
                                    }
                                }
                                .size(40.dp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 35.dp, bottom = 30.dp, end = 35.dp, top = 0.dp)
                        .fillMaxSize()
                        .border(
                            width = 2.dp,
                            color = Color(0xFF0047B3),
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                ) {
                    var ltoMemo by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                        mutableStateOf(TextFieldValue())
                    }
//                    ltoMemo = TextFieldValue("오늘은 LTO 1, LTO 2를 실시했습니다. \n오늘 실시한 LTO 1, LTO 2와 관련된 교육을 가정에서도 진행해주시면 더욱 효과적입니다. ",)
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        value = ltoMemo,
                        onValueChange = { ltoMemo = it },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontFamily = fontFamily_Inter,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF0047B3),
                            letterSpacing = 0.16.sp,
                            textAlign = TextAlign.Start,
                        ),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = true,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                    )
                }
            }
        }
        AnimatedVisibility(visible = expandedState) {
            val dummyReportSTOList = mutableListOf<StoResponse>().toMutableStateList()
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(550.dp)
                    .background(Color(0xFFE7EBF0))
            ) {
                items(dummySTOList) { sto ->
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(300.dp)
                            .padding(start = 15.dp, end = 15.dp, bottom = 30.dp, top = 10.dp)
                            .border(
                                width = 2.dp,
                                color = if (dummyReportSTOList.contains(sto)) {
                                    Color(0xFF0047B3)
                                } else {
                                    Color.Transparent
                                },
                                shape = RoundedCornerShape(10.dp)
                            )
                            .shadow(
                                elevation = 16.dp,
                                spotColor = Color(0x40000000),
                                ambientColor = Color(0x40000000),
                                clip = false
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                if (dummyReportSTOList.contains(sto)) {
                                    dummyReportSTOList.remove(sto)
                                } else {
                                    if (dummyReportSTOList.size < 4) {
                                        dummyReportSTOList.add(sto)
                                    } else {
                                        Toasty
                                            .warning(
                                                context,
                                                "하나의 LTO 당 4개의 STO를 알림장에 넣을 수 있습니다.",
                                                Toast.LENGTH_SHORT,
                                                true
                                            )
                                            .show()

                                    }
                                }
                                Log.d("dummy", dummyReportSTOList.toString())

                            }
                    ) {
                        Text(
                            text = sto.name,
                            style = TextStyle(
                                fontSize = 24.sp,
                                lineHeight = 15.sp,
                                fontFamily = fontFamily_Inter,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF000000),
                            ),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = Modifier.padding(
                                start = 10.dp,
                                top = 20.dp,
                                bottom = 20.dp,
                                end = 10.dp
                            )
                        )
                        NoticeGraph(
                            plusList = listOf(10f, 20f, 10f, 40f, 95f),
                            minusList = listOf(70f, 65f, 40f, 50f, 3f),
                            dateList = listOf("11/01", "11/02", "11/03", "11/06", "11/08")
                        )
                    }
                }

            }
        }

    }

}


@Composable
fun NoticeGraph(
    plusList: List<Float>,
    minusList: List<Float>,
    dateList: List<String>
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
            successLine.add(Point(successLineIndex, 90f))
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
//                        IntersectionPoint(
//                            color = Color.Green,
//                            radius = 5.dp
//                        ),
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
//                        IntersectionPoint(
//                            color = Color.Red
//                        ),
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