@file:Suppress("NAME_SHADOWING")

package inu.thebite.tory.screens.DataScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.android.animation.SegmentType
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.isNotNull
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

import inu.thebite.tory.ChildViewModel
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.AddLTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.AddSTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.DevelopZoneRow
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.UpdateLTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.UpdateSTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.LTODetailsRow
import inu.thebite.tory.screens.DataScreen.Compose.LTOItemsRow
import inu.thebite.tory.screens.DataScreen.Compose.STODetailTableAndGameResult
import inu.thebite.tory.screens.DataScreen.Compose.STODetailsRow
import inu.thebite.tory.screens.DataScreen.Compose.STOItemsRow

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun DataScreen (
    ltoViewModel: LTOViewModel = viewModel(),
    stoViewModel: STOViewModel = viewModel(),
    stoDetailViewModel: STODetailViewModel = viewModel(),
    graphViewModel: GraphViewModel = viewModel(),
    childViewModel: ChildViewModel = viewModel()
) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    val (updateLTOItem, setUpdateLTOItem) = rememberSaveable {
        mutableStateOf(false)
    }
    val (updateSTOItem, setUpdateSTOItem) = rememberSaveable {
        mutableStateOf(false)
    }
    val (addLTOItem, setAddLTOItem) = rememberSaveable {
        mutableStateOf(false)
    }
    val (addSTOItem, setAddSTOItem) = rememberSaveable {
        mutableStateOf(false)
    }
    val (gameDialog, setGameDialog) = rememberSaveable {
        mutableStateOf(false)
    }
    val (selectedLTO, setSelectedLTO) = rememberSaveable {
        mutableStateOf("")
    }
    val (selectedSTO, setSelectedSTO) = rememberSaveable {
        mutableStateOf("")
    }

    val (selectedDEVIndex, setSelectedDEVIndex) = rememberSaveable {
        mutableStateOf(0)
    }
    val (selectedLTOIndex, setSelectedLTOIndex) = rememberSaveable {
        mutableStateOf(0)
    }
    val (selectedSTOIndex, setSelectedSTOIndex) = rememberSaveable {
        mutableStateOf(0)
    }
    val (ltoDetailListIndex, setLTODetailListIndex) = rememberSaveable {
        mutableStateOf(-1)
    }
    val (stoDetailListIndex, setSTODetailListIndex) = rememberSaveable {
        mutableStateOf(-1)
    }

    val (isLTOGraphSelected, setIsLTOGraphSelected) = rememberSaveable {
        mutableStateOf(false)
    }
    val (isSTOGraphSelected, setIsSTOGraphSelected) = rememberSaveable {
        mutableStateOf(false)
    }

    val (ltoProgressState, setLTOProgressState) = rememberSaveable {
        mutableStateOf(-1)
    }
    val (stoProgressState, setSTOProgressState) = rememberSaveable {
        mutableStateOf(-1)
    }
    val (stoDetailState, setSTODetailState) = rememberSaveable {
        mutableStateOf(mutableListOf<String>())
    }
    val (selectedSTODetail, setSelectedSTODetail) = rememberSaveable {
        mutableStateOf(mutableListOf<String>())
    }
    val (selectedSTODetailGameDataIndex, setSelectedSTODetailGameDataIndex) = rememberSaveable {
        mutableStateOf(0)
    }
//    //Chart
//    val refreshDataset = remember {
//        mutableIntStateOf(0)
//    }
//
//    val modelProducer = remember {
//        ChartEntryModelProducer()
//    }
//
//    val datasetForModel = rememberSaveable {
//        mutableStateListOf(listOf<FloatEntry>())
//    }
//    val datasetLineSpec = remember {
//        arrayListOf<LineChart.LineSpec>()
//    }
//
//    val chartScrollState = rememberChartScrollState()
//
//    LaunchedEffect(key1 = refreshDataset){
//        //rebuild chart
//        datasetForModel.clear()
//        datasetLineSpec.clear()
//        var xPos = 0f
//        val dataPoints = arrayListOf<FloatEntry>()
//        datasetLineSpec.add(
//            LineChart.LineSpec(
//                lineColor = Green.toArgb(),
//                lineBackgroundShader = DynamicShaders.fromBrush(
//                    brush = Brush.verticalGradient(
//                        listOf(
//                            Green.copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_START),
//                            Green.copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_END)
//
//                        )
//                    )
//                )
//            )
//        )
//        for(i in 1..100){
//            val randomYFloat = (1..1000).random().toFloat()
//            dataPoints.add(FloatEntry(x=xPos, y=randomYFloat))
//            xPos += 1f
//        }
//
//        datasetForModel.add(dataPoints)
//
//        modelProducer.setEntries(datasetForModel)
//    }



    val developZoneItems = listOf<String>(
        "1. 학습준비",
        "2. 매칭",
        "3. 동작모방",
        "4. 언어모방",
        "5. 변별",
        "6. 지시따라하기",
        "7. 요구하기",
        "8. 명명하기",
        "9. 인트라",
        "10. 가나다"
    )




    //LTO 추가 Dialog
    if (addLTOItem) {
        AddLTOItemDialog(
            setAddLTOItem = { setAddLTOItem(false) },
            ltoViewModel = ltoViewModel,
            stoViewModel = stoViewModel,
            stoDetailViewModel = stoDetailViewModel,
            selectedDevIndex = selectedDEVIndex,
        )
    }

    //LTO 추가 Dialog
    if (updateLTOItem) {
        UpdateLTOItemDialog(
            setUpdateLTOItem = {setUpdateLTOItem(false)},
            ltoViewModel = ltoViewModel,
            graphViewModel = graphViewModel,
            childViewModel = childViewModel,
            selectedSTO = selectedSTO,
            selectedLTO = selectedLTO,
            selectedDevIndex = selectedDEVIndex,
            selectedLTOIndex = selectedLTOIndex,
            setSelectedLTO = {setSelectedLTO(it)}
        )
    }

    //STO 추가 Dialog
    if (addSTOItem) {
        AddSTOItemDialog(
            setAddSTOItem = { setAddSTOItem(false) },
            stoViewModel = stoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTOIndex = selectedLTOIndex,
            stoDetailViewModel = stoDetailViewModel,
        )
    }
    //STO 추가 Dialog
    if (updateSTOItem) {
        UpdateSTOItemDialog(
            stoViewModel = stoViewModel,
            stoDetailViewModel = stoDetailViewModel,
            childViewModel = childViewModel,
            graphViewModel = graphViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTOIndex = selectedLTOIndex,
            selectedSTOIndex = selectedSTOIndex,
            selectedSTO = selectedSTO,
            selectedLTO = selectedLTO,
            setUpdateSTOItem = {setUpdateSTOItem(it)},
            setSelectedSTO = {setSelectedSTO(it)},
            setSelectedSTODetailList = {setSelectedSTODetail(it.toMutableList())}
        )
    }

    if(gameDialog){
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            onDismissRequest = { setGameDialog(false) }
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f)
                        .background(MaterialTheme.colorScheme.secondary),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                setGameDialog(false)
                            },
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )

                }
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)) {
                    Button(onClick = {
                    }) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                    }

                }
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
        //발달영역 ITEM------------------------------------------------------------------------------
        DevelopZoneRow(
            developZoneItems = developZoneItems,
            selectDevelopItem = {
                setSelectedDEVIndex(developZoneItems.indexOf(it))
                setSelectedLTO("")
                setSelectedSTO("")
                Log.e("버그위치", "버그위치")
                setSelectedSTODetailGameDataIndex(0)
            }
        )
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
        //LTO ITEM----------------------------------------------------------------------------------
        LTOItemsRow(
            ltoViewModel = ltoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTO = selectedLTO,
            setAddLTOItem = {setAddLTOItem(it)},
            selectLTOItem = { it: String, progressState:Int ->
                setSelectedSTO("")
                setSelectedLTO(it)
                setSelectedLTOIndex(ltoViewModel.getLTO(selectedDEVIndex).first.indexOf(it))
                setLTODetailListIndex(progressState)
                setSelectedSTODetailGameDataIndex(0)
            },
            deleteLTOItem = {
                stoViewModel.removeLTOAtIndex(ltoViewModel.getLTO(selectedDEVIndex).first.indexOf(it),selectedDEVIndex)
                stoDetailViewModel.removeLTOAtIndex(ltoViewModel.getLTO(selectedDEVIndex).first.indexOf(it),selectedDEVIndex)
                ltoViewModel.removeLTO(selectedDEVIndex, it)
                setSelectedLTO("")
                setSelectedSTO("")
            }
        )
        //LTO Detail--------------------------------------------------------------------------------
        LTODetailsRow(
            selectedLTO = selectedLTO,
            isGraphSelected = isLTOGraphSelected,
            setGraphSelected = { setIsLTOGraphSelected(it) },
            setDetailListIndex = { setLTODetailListIndex(it) },
            ltoViewModel = ltoViewModel,
            selectedDevIndex = selectedDEVIndex,
            setProgressState = { setLTOProgressState(it) },
            ltoDetailListIndex = ltoDetailListIndex,
            setLTOUpdateDialog = {setUpdateLTOItem(it)}
        )
        //STO ITEM ---------------------------------------------------------------------------------
        STOItemsRow(
            stoViewModel = stoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTO = selectedLTO,
            selectedSTO = selectedSTO,
            selectedLTOIndex = selectedLTOIndex,
            setAddSTOItem = { setAddSTOItem(it) },
            selectSTOItem = { it: String, progressState:Int ->
                val newIndex = stoViewModel.getSTO(selectedLTOIndex, selectedDEVIndex).first.indexOf(it)
                setSelectedSTO(it)
                setSelectedSTOIndex(newIndex)
                Log.e("선택 STO", selectedSTOIndex.toString())
                // 이 부분을 LaunchedEffect로 감싸서 업데이트를 다음 프레임으로 보내줍니다.
                //selectedSTODetailList가 바로 적용되지 않고 한턴씩 밀림 UI에 볂
                setSelectedSTODetail(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDEVIndex, newIndex).first.toMutableList())
                setSTODetailListIndex(progressState)
                setSelectedSTODetailGameDataIndex(0)
            },
            deleteSTOItem = {
                stoDetailViewModel.removeSTODetail(selectedLTOIndex, selectedDEVIndex, listOf(it,selectedSTODetail[1],selectedSTODetail[2],selectedSTODetail[3],selectedSTODetail[4],selectedSTODetail[5],selectedSTODetail[6]))
                stoViewModel.removeSTO(selectedLTOIndex, selectedDEVIndex,it)
                setSelectedSTO("")
                setSelectedSTODetailGameDataIndex(0)
            }
        )
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
//      STO Details Row---------------------------------------------------------------------------
        STODetailsRow(
            selectedSTO = selectedSTO,
            isSTOGraphSelected = isSTOGraphSelected,
            setIsSTOGraphSelected = { setIsSTOGraphSelected(it) },
            stoViewModel = stoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTOIndex = selectedLTOIndex,
            stoDetailListIndex = stoDetailListIndex,
            setProgressState = { setSTOProgressState(it) },
            setSTODetailIndex = { setSTODetailListIndex(it) },
            setUpdateSTOItem = {setUpdateSTOItem(it)},
            gameStart = {
                setGameDialog(true)
            }
        )
        //STO Detail 내용 및 게임결과
        STODetailTableAndGameResult(
            selectedDEVIndex = selectedDEVIndex,
            selectedLTOIndex = selectedLTOIndex,
            selectedSTOIndex = selectedSTOIndex,
            selectedSTO = selectedSTO,
            selectedSTODetail = selectedSTODetail,
            selectedSTODetailGameDataIndex = selectedSTODetailGameDataIndex,
            stoDetailViewModel = stoDetailViewModel,
            setSelectedSTODetailGameDataIndex = {setSelectedSTODetailGameDataIndex(it)},
            graphViewModel = graphViewModel,
            ltoViewModel = ltoViewModel,
            childViewModel = childViewModel
        )
        //게임준비
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                .border(4.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
        ) {

        }
        //그래프
        if(isLTOGraphSelected){
            val developZoneItems = listOf<String>(
                "1. 학습준비",
                "2. 매칭",
                "3. 동작모방",
                "4. 언어모방",
                "5. 변별",
                "6. 지시따라하기",
                "7. 요구하기",
                "8. 명명하기",
                "9. 인트라",
                "10. 가나다"
            )

            LazyRow(
                modifier = Modifier
                    .height(600.dp)
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .border(4.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
            ) {
                items(stoViewModel.getSTO(selectedLTOIndex, selectedDEVIndex).first){ stoName ->
                    if(graphViewModel.getGraph(developZoneItems[selectedDEVIndex], selectedLTO, stoName, childViewModel.selectedChildClass, childViewModel.selectedChildName).isNotNull()){
                        val steps = 5
                        var plusIndex = 0f
                        var minusIndex = 0f
                        var limitLineIndex = 0f
                        var successLineIndex = 0f
                        var minLineIndex = 0f

                        val limitLine = mutableListOf<Point>()
                        for(a in graphViewModel.getGraph(developZoneItems[selectedDEVIndex], selectedLTO, stoName, childViewModel.selectedChildClass, childViewModel.selectedChildName)!!.plusRatio){
                            limitLine.add(Point(limitLineIndex, 100f))
                            limitLineIndex += 1f
                        }
                        val minLine = mutableListOf<Point>()
                        for(a in graphViewModel.getGraph(developZoneItems[selectedDEVIndex], selectedLTO, stoName, childViewModel.selectedChildClass, childViewModel.selectedChildName)!!.plusRatio){
                            minLine.add(Point(minLineIndex, 0f))
                            minLineIndex += 1f
                        }
                        val successLine = mutableListOf<Point>()
                        for(b in graphViewModel.getGraph(developZoneItems[selectedDEVIndex], selectedLTO, stoName, childViewModel.selectedChildClass, childViewModel.selectedChildName)!!.plusRatio){
                            successLine.add(Point(successLineIndex, 90f))
                            successLineIndex += 1f
                        }
                        val pointsData1 = mutableListOf<Point>()
                        for(plus in graphViewModel.getGraph(developZoneItems[selectedDEVIndex], selectedLTO, stoName, childViewModel.selectedChildClass, childViewModel.selectedChildName)!!.plusRatio){
                            pointsData1.add(Point(plusIndex, plus.toInt().toFloat()))
                            Log.e("성공값", plus.toInt().toFloat().toString())
                            plusIndex += 1f
                        }
                        val pointsData2 = mutableListOf<Point>()
                        for(minus in graphViewModel.getGraph(developZoneItems[selectedDEVIndex], selectedLTO, stoName, childViewModel.selectedChildClass, childViewModel.selectedChildName)!!.minusRatio){
                            pointsData2.add(Point(minusIndex, minus.toInt().toFloat()))
                            Log.e("실패값", minus.toInt().toFloat().toString())
                            minusIndex += 1f
                        }
                        val xAxisLabelList = graphViewModel.getGraph(developZoneItems[selectedDEVIndex], selectedLTO, stoName, childViewModel.selectedChildClass, childViewModel.selectedChildName)!!.date
                        val xAxisData = AxisData.Builder()
                            .axisStepSize(50.dp)
                            .backgroundColor(Color.Transparent)
                            .steps(pointsData1.size - 1)
                            .labelData { i -> "          "+xAxisLabelList[i.toInt()].toString().takeLast(5) }
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
                                            color = Green,
                                            lineType = LineType.Straight(isDotted = false)
                                        ),
                                        IntersectionPoint(
                                            color = Green
                                        ),
                                        SelectionHighlightPoint(color = Green),
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

    }
}












