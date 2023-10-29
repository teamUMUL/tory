package inu.thebite.tory.screens.homescreen
import android.graphics.Paint
import android.os.Bundle
import android.accounts.AuthenticatorDescription
import android.text.AutoText
import android.text.BoringLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonColors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.ui.theme.Black
import inu.thebite.tory.ui.theme.LightGray
import inu.thebite.tory.ui.theme.blueGray
import inu.thebite.tory.ui.theme.brightBlue
import inu.thebite.tory.ui.theme.gray
import inu.thebite.tory.ui.theme.green
import inu.thebite.tory.ui.theme.orange
import inu.thebite.tory.ui.theme.purple
import inu.thebite.tory.ui.theme.redOrange
import inu.thebite.tory.ui.theme.white
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.atan2



@Composable
fun pieChartPreview(
    modifier: Modifier = Modifier,
    radius:Float = 250f
){
    Column(
        modifier = modifier
            .padding(40.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
        ){

            PieChart(
                modifier = Modifier
                    .size(400.dp),
                input = listOf(
                    PieChartInput(
                        color = Color(0xFF97DFFC),
                        value = 10,
                        number = 3,
                        size = Size(width = radius*1.3f, height = radius*1.3f),
                        topLeft = radius*1.3f ,
                        description = "학습준비"

                    ),
                    PieChartInput(
                        color = Color(0xFF93CAF6),
                        value = 10,
                        number = 2,
                        size = Size(width = radius*1.2f, height = radius*1.2f ),
                        topLeft = radius*1.2f ,
                        description = "매칭"
                    ),
                    PieChartInput(
                        color = Color(0xFF8EB5F0),
                        value = 10,
                        number = 5,
                        size = Size(width = radius*1.5f, height = radius*1.5f),
                        topLeft = radius*1.5f ,
                        description = "동작"
                    ),
                    PieChartInput(
                        color = Color(0xFF858AE3),
                        value = 10,
                        number = 8,
                        size = Size(width = radius*1.8f, height = radius*1.8f),
                        topLeft = radius*1.8f ,
                        description = "언어"
                    ),
                    PieChartInput(
                        color = Color(0xFF7364D2),
                        value = 10,
                        number =4,
                        size = Size(width = radius*1.4f, height = radius*1.4f),
                        topLeft = radius*1.4f ,
                        description = "변별"
                    ),
                    PieChartInput(
                        color = Color(0xFF613DC1),
                        value = 10,
                        number = 0,
                        size = Size(width = radius*0f, height = radius*0f),
                        topLeft = radius*0f ,
                        description = "지시"
                    ),
                    PieChartInput(
                        color = Color(0xFF5829A7),
                        value = 10,
                        number = 1,
                        size = Size(width = radius*1.1f, height = radius*1.1f),
                        topLeft = radius*1.1f ,
                        description = "명명"
                    ),
                    PieChartInput(
                        color = Color(0xFF4E148C),
                        value = 10,
                        number = 0,
                        size = Size(width = radius*0f, height = radius*0f),
                        topLeft = radius*0f ,
                        description = "요구"
                    ),
                    PieChartInput(
                        color = Color(0xFF461177),
                        value = 10,
                        number = 0,
                        size = Size(width = radius*0f, height = radius*0f),
                        topLeft = radius*0f ,
                        description = "인트라"
                    ),
                    PieChartInput(
                        color = Color(0xFF3D0E61),
                        value = 10,
                        number = 0,
                        size = Size(width = radius*0f, height = radius*0f),
                        topLeft = radius*0f ,
                        description = "사회성"
                    ),
                ),
//                centerText = "total\ntask"
            )
        }
    }
}
@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    radius:Float = 250f,  //색 원
    innerRadius:Float = 20f,  //중앙 하얀 원
    transparentWidth:Float = 35f, //하얀 원 테두리
    input:List<PieChartInput>,
    centerText:String = "" //하얀 원 중앙에 들어가는 텍스트
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var inputList by remember {  // 도넛 값을 바꾸기 위해서
        mutableStateOf(input)
    }
    var isCenterTapped by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true){
                    detectTapGestures(
                        onTap = { offset->
                            val tapAngleInDegrees = (-atan2(     //탭 각도
                                x = circleCenter.y - offset.y,
                                y = circleCenter.x - offset.x
                            ) * (180f / PI).toFloat() - 90f).mod(360f)
                            val centerClicked = if(tapAngleInDegrees<90) {
                                offset.x<circleCenter.x+innerRadius && offset.y<circleCenter.y+innerRadius
                            }else if(tapAngleInDegrees<180){
                                offset.x>circleCenter.x-innerRadius && offset.y<circleCenter.y+innerRadius
                            }else if(tapAngleInDegrees<270){
                                offset.x>circleCenter.x-innerRadius && offset.y>circleCenter.y-innerRadius
                            }else{
                                offset.x<circleCenter.x+innerRadius && offset.y>circleCenter.y-innerRadius
                            }

                            if(centerClicked){

                                inputList = inputList.map {
                                    it.copy(isTapped = !isCenterTapped)
                                }
                                isCenterTapped = !isCenterTapped

                            }else{
                                val anglePerValue = 360f/input.sumOf { //각도 당 가중치
                                    it.value
                                }
                                var currAngle = 0f   //현재 각도
                                inputList.forEach { pieChartInput ->

                                    currAngle += pieChartInput.value * anglePerValue
                                    if(tapAngleInDegrees<currAngle){
                                        val description = pieChartInput.description
                                        inputList = inputList.map {
                                            if(description == it.description){
                                                it.copy(isTapped = !it.isTapped)
                                            }else{
                                                it.copy(isTapped = false)
                                            }
                                        }
                                        return@detectTapGestures
                                    }
                                }
                            }
                        }
                    )
                }
        ){
            //----------------그림 그리는 부분---------------------------//
            drawContext.canvas.nativeCanvas.apply {
                drawCircle(
                    circleCenter.x,
                    circleCenter.y,
                    radius,
                    Paint().apply {
                        color = LightGray.toArgb()
//                        setShadowLayer(10f,0f,0f, Black.toArgb())
                    }
                )
            }


            val width = size.width
            val height = size.height
            circleCenter = Offset(x= width/2f,y= height/2f)

            val totalValue = input.sumOf {//100
                it.value
            }
            val anglePerValue = 360f/totalValue  //36
            var currentStartAngle = 0f

            inputList.forEach { pieChartInput ->
                val scale = if(pieChartInput.isTapped) 1.1f else 1.0f // 사이즈 커지는 정도
                val angleToDraw = pieChartInput.value * anglePerValue

                //----------------------파이차트-------------------
                scale(scale){
                    drawArc(
                        color = pieChartInput.color, // 파이차트에 들어가는 색
                        startAngle = currentStartAngle,
                        sweepAngle = angleToDraw,
                        useCenter = true,
                        size = pieChartInput.size,
                        topLeft = Offset(
                            (width-pieChartInput.topLeft)/2f,
                            (height -pieChartInput.topLeft)/2f
                        )
                    )
                    currentStartAngle += angleToDraw
                }

                var rotateAngle = currentStartAngle-angleToDraw/2f-90f
                var factor = 1f
                if(rotateAngle>90f){
                    rotateAngle = (rotateAngle+180).mod(360f)
                    factor = -0.92f
                }

                val percentage = (pieChartInput.value/totalValue.toFloat()*100).toInt()

                drawContext.canvas.nativeCanvas.apply {  // 각각 조각 내의 글씨
                    if(percentage>3){
                        rotate(rotateAngle){
                            drawText(
                                "${pieChartInput.description}",
                                circleCenter.x,
                                circleCenter.y+(radius-(radius-innerRadius)/1.5f)*factor,
                                Paint().apply {
                                    textSize = 10.sp.toPx()
                                    textAlign = Paint.Align.CENTER
                                    color = white.toArgb()
                                }
                            )
                        }
                    }
                }
                if(pieChartInput.isTapped){
                    val tabRotation = currentStartAngle - angleToDraw - 90f
//                    rotate(tabRotation){
//                        drawRoundRect(
//                            topLeft = circleCenter,
//                            size = Size(12f,radius*1.2f),
//                            color = white, //탭했을 떄 나오는 선
//                            cornerRadius = CornerRadius(15f,15f)
//                        )
//                    }
//                    rotate(tabRotation+angleToDraw){
//                        drawRoundRect(
//                            topLeft = circleCenter,
//                            size = Size(12f,radius*1.2f),
//                            color = white,
//                            cornerRadius = CornerRadius(15f,15f)
//                        )
//                    }
                    rotate(rotateAngle){
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                "${pieChartInput.description}: ${pieChartInput.number}",
                                circleCenter.x,
                                circleCenter.y + radius*1.3f*factor,
                                Paint().apply {
                                    textSize = 22.sp.toPx()
                                    textAlign = Paint.Align.CENTER
                                    color = gray.toArgb()
                                    isFakeBoldText = true
                                }
                            )
                        }
                    }


                }
            }

//            if(inputList.first().isTapped){   // 1번째 부채콜을 터치했을 때  선 한쪽 추가
//                rotate(-90f){
//                    drawRoundRect(
//                        topLeft = circleCenter,
//                        size = Size(12f,radius*1.2f),
//                        color = white,
//                        cornerRadius = CornerRadius(15f,15f)
//                    )
//                }
//            }
//            drawContext.canvas.nativeCanvas.apply {
//                drawCircle(
//                    circleCenter.x,
//                    circleCenter.y,
//                    radius,
//                    Paint().apply {
//                        color = white.copy(alpha = 0.6f).toArgb()
////                        setShadowLayer(10f,0f,0f, Black.toArgb())
//                    }
//                )
//            }



        }
//        Text(
//            centerText,
//            modifier = Modifier
//                .width(Dp(innerRadius/1.5f))
//                .padding(25.dp),
//            fontWeight = FontWeight.SemiBold,
//            fontSize = 17.sp,
//            textAlign = TextAlign.Center
//        )

    }
}

data class PieChartInput(
    val color:Color,
    val value:Int,
    val number:Int,
    val size: Size,
    val topLeft : Float,
    val description:String,
    val isTapped:Boolean = false
)