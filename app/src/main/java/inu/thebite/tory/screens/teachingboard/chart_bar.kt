package inu.thebite.tory.screens.teachingboard
import android.graphics.Paint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Top
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.round

@Composable
fun chart_bar(
    modifier: Modifier = Modifier
){
    Column(
        modifier= Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier  // 해더 부분
            .padding(top = 10.dp, start = 30.dp, end = 20.dp, bottom = 30.dp)
            .height(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row (modifier = Modifier.weight(2f)){
                androidx.compose.material3.Text(
                    text = " Individual Child Daily",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                        letterSpacing = 0.14.sp
                    )
                )
                Spacer(modifier = Modifier.width(20.dp))
                androidx.compose.material3.Text(
                    text = "January",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        letterSpacing = 0.14.sp,
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1.5f))
            Row(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .width(8.dp)
                        .height(9.45067.dp)
                        .background(
                            color = Color(0xFF2CB67D),
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                )
                Spacer(modifier = Modifier.width(8.dp))
                androidx.compose.material3.Text(
                    text = "23.07",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFA6ACBE),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.14.sp,
                    )
                )
                Spacer(modifier = Modifier.width(15.dp))
                Box(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .width(8.dp)
                        .height(9.45067.dp)
                        .background(
                            color = Color(0xFF7F5AF0),
                            shape = RoundedCornerShape(size = 10.dp)
                        ),
                )
                Spacer(modifier = Modifier.width(8.dp))
                androidx.compose.material3.Text(
                    text = "23.10",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFA6ACBE),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.14.sp,
                    )
                )
            }


        }


        CustomBarGraph()
    }

}

@Composable
fun CustomBarGraph() {
    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val dataList_01 = mutableListOf(10, 13, 20, 7, 11, 5, 2, 0, 6, 7)
        val dataList_02= mutableListOf( 13, 10, 4, 7, 2, 20, 1, 3, 5, 6)
        val floatValue_01 = mutableListOf<Float>()
        val floatValue_02 = mutableListOf<Float>()
        val datesList = mutableListOf(1,2,3,4,5,6,7,8,9,10)

        dataList_01.forEachIndexed { index, value ->

            floatValue_01.add(index = index, element = value.toFloat() / dataList_01.max().toFloat())
        }
        dataList_02.forEachIndexed { index, value ->

            floatValue_02.add(index = index, element = value.toFloat() / dataList_02.max().toFloat())
        }
        BarGraph(
            graphBarData_01 = floatValue_01,
            graphBarData_02 = floatValue_02,
            xAxisScaleData = datesList,
            barData_ = dataList_01,
            barData__ = dataList_02,
            height = 300.dp,
            roundType = BarType.TOP_CURVED,
            barWidth = 50.dp,     // 바그래프 너비
            barColor_02 = Color(0xFF7F5AF0),
            barColor_01 = Color(0xFF2CB67D),
            barArrangement = Arrangement.SpaceEvenly
        )
    }
}




@Composable
fun BarGraph(
    graphBarData_01: List<Float>,
    graphBarData_02: List<Float>,
    xAxisScaleData: List<Int>,
    barData_: List<Int>,
    barData__: List<Int>,
    height: Dp,
    roundType: BarType,
    barWidth: Dp,
    barColor_01: Color,
    barColor_02: Color,
    barArrangement: Arrangement.Horizontal
) {

    val barData by remember {
        mutableStateOf(barData_+0)
    }




    //화면 너비와 높이를 얻으려면 LocalConfiguration을 사용.

    val configuration = LocalConfiguration.current
    // getting screen width
    val width = configuration.screenWidthDp.dp


    //X축 스케일의 하단 높이
    val xAxisScaleHeight = 30.dp

    val yAxisScaleSpacing by remember {
        mutableStateOf(100f)
    }
    val yAxisTextWidth by remember {
        mutableStateOf(100.dp)
    }

    val barShap =
        when (roundType) {
            BarType.CIRCULAR_TYPE -> CircleShape
            BarType.TOP_CURVED -> RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)
        }

    val density = LocalDensity.current
    // y-axis scale text paint
    val textPaint = remember(density) {
        Paint().apply {
            color = Color.Black.hashCode()
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    // y축 스케일의 y좌표에 대해 y축 스케일을 나타내는 수평 점선을 생성
    val yCoordinates = mutableListOf<Float>()
    // 점선
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    // x축 수평선을 연결하는 x축 눈금 위의 수직선 높이
//    val lineHeightXAxis = 10.dp
    val horizontalLineHeight = 2.dp

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {

        // y축 스케일과 y축 스케일을 나타내는 그래프의 가로 점선
        Column(
            modifier = Modifier
                .padding(top = xAxisScaleHeight, end = 3.dp)
                .height(height)
                .fillMaxWidth(),
            horizontalAlignment = CenterHorizontally
        ) {

            Canvas(modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxSize()) {

                // y축 글씨
                val yAxisScaleText = (barData.max()) / 3f
                (0..3).forEach { i ->
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            round(barData.min() + yAxisScaleText * i).toString(),
                            20f,
                            size.height - yAxisScaleSpacing - i * size.height / 3.4f,
                            textPaint
                        )
                    }
                    yCoordinates.add(size.height - yAxisScaleSpacing - i * size.height / 3.4f)
                }

                // 수평 점선
                (1..3).forEach {
                    drawLine(
                        start = Offset(x = yAxisScaleSpacing +20f, y = yCoordinates[it]),
                        end = Offset(x= size.width, y = yCoordinates[it]),
                        color = Color.Gray,
                        strokeWidth = 1f,
                        pathEffect = pathEffect
                    )
                }

            }

        }

        // 막대 그래프와 X축 눈금이 포함된 그래프
        Box(
            modifier = Modifier
//                .padding(start = 70.dp)
                .width(width - yAxisTextWidth)
                .height(height + xAxisScaleHeight),
            contentAlignment = Alignment.BottomCenter
        ) {

            Box{
                // Graph
                Row (
                    modifier = Modifier
                        .width(width - yAxisTextWidth)
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement =Arrangement
                        .spacedBy(
                            space = 60.dp,
                            alignment = Alignment.CenterHorizontally
                        )
                ){

                    graphBarData_02.forEachIndexed { index, value ->

                        var animationTriggered by remember {
                            mutableStateOf(false)
                        }

                        val graphBarHeight_02 by animateFloatAsState(
                            targetValue = if (animationTriggered) value else 0f,
                            animationSpec = tween(
                                durationMillis = 1000,
                                delayMillis = 0
                            )
                        )
                        LaunchedEffect(key1 = true) {
                            animationTriggered = true
                        }

                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Top,
                            horizontalAlignment = CenterHorizontally
                        ) {

                            // 각각의 그래프
                            Box(
                                modifier = Modifier
//                                    .padding(bottom = 5.dp)
                                    .width(15.dp)
                                    .height(height - 10.dp)
                                    .background(Color.Transparent),
                                contentAlignment = BottomCenter
                            ) {

                                Box(
                                    modifier = Modifier
                                        .clip(barShap)
                                        .width(6.dp)
                                        .fillMaxHeight(graphBarHeight_02)
                                        .background(barColor_01)
                                )
                            }



                        }

                    }
                }
                Row(
                    modifier = Modifier
                        .padding(start = 40.dp)
                        .width(width - yAxisTextWidth),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement
                        .spacedBy(
                            space = 60.dp,
                            alignment = Alignment.CenterHorizontally
                        )
                ) {
                    graphBarData_01.forEachIndexed { index, value ->

                        var animationTriggered by remember {
                            mutableStateOf(false)
                        }

                        val graphBarHeight by animateFloatAsState(
                            targetValue = if (animationTriggered) value else 0f,
                            animationSpec = tween(
                                durationMillis = 1000,
                                delayMillis = 0
                            )
                        )

                        LaunchedEffect(key1 = true) {
                            animationTriggered = true
                        }

                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Top,
                            horizontalAlignment = CenterHorizontally
                        ) {

                            // 각각의 그래프
                            Box(
                                modifier = Modifier
                                    .padding(bottom = 5.dp)
//                                .clip(barShap)
                                    .width(15.dp)
                                    .height(height - 10.dp)
                                    .background(Color.Transparent),
                                contentAlignment = BottomCenter
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(barShap)
                                        .width(6.dp)
                                        .fillMaxHeight(graphBarHeight)
                                        .background(barColor_02)
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .height(xAxisScaleHeight),
                                verticalArrangement = Top,
                                horizontalAlignment = CenterHorizontally
                            ) {


//                                Box(
//                                    modifier = Modifier
//                                        .clip(
//                                            RoundedCornerShape(
//                                                bottomStart = 2.dp,
//                                                bottomEnd = 2.dp
//                                            )
//                                        )
//                                        .padding(end = 10.dp)
//                                        .width(horizontalLineHeight)
//                                        .height(lineHeightXAxis)
//                                        .background(color = Color.Gray)
//                                )
                                // scale x-axis
                                Text(
                                    modifier = Modifier.padding(bottom = 3.dp, end = 5.dp),
                                    text = xAxisScaleData[index].toString(),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Start,
                                    color = Color.Gray
                                )

                            }

                        }

                    }
                }
            }

            Column(      // x축
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                horizontalAlignment = CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .padding(bottom = xAxisScaleHeight + 3.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .fillMaxWidth()
                        .height(horizontalLineHeight)
                        .background(Color.Gray)
                )

            }


        }


    }

}