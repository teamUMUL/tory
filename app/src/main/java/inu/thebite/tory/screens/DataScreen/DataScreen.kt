@file:Suppress("NAME_SHADOWING")

package inu.thebite.tory.screens.DataScreen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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
import androidx.compose.ui.res.painterResource
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
import inu.thebite.tory.R
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.AddLTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.AddSTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.DevelopZoneRow
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.UpdateLTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.UpdateSTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.GraphRow
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
    childViewModel: ChildViewModel = viewModel(),
    stoViewModelByDataClass: STOViewModelByDataClass = viewModel()
) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    val (stoList, setStoList) = rememberSaveable {
        mutableStateOf(emptyList<STO>())
    }
    val (newStoName, setNewStoName) = rememberSaveable {
        mutableStateOf("")
    }
    val (newStoDescription, setNewStoDescription) = rememberSaveable {
        mutableStateOf("")
    }

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
    val (addGameItem, setAddGameItem) = rememberSaveable {
        mutableStateOf(false)
    }
    val (selectedLTO, setSelectedLTO) = rememberSaveable {
        mutableStateOf("")
    }
    val (selectedSTO, setSelectedSTO) = rememberSaveable {
        mutableStateOf("")
    }
    val (selectedSTOId, setSelectedSTOId) = rememberSaveable {
        mutableStateOf(-1)
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

    val selectedIdxMap = remember {
        mutableStateMapOf<String, Int>()
    }
    val selectedGameItems = remember { mutableStateListOf<String>() }

    val (mainGameItem, setMainGameItem) = rememberSaveable {
        mutableStateOf("")
    }

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
            childViewModel = childViewModel,
            stoViewModelByDataClass = stoViewModelByDataClass,
            selectedLTO = selectedLTO
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

    if(addGameItem){

        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            onDismissRequest = {
                setAddGameItem(false)
            }
        ){
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                shape = RoundedCornerShape(10.dp),
                color = Color.White
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)

                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.1f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            modifier = Modifier
                                .padding(start = 20.dp),
                            text = "교육준비",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            modifier = Modifier
                                .padding(end = 20.dp),
                            onClick ={
                                setAddGameItem(false)
                            }
                        ){
                            Icon(
                                modifier = Modifier
                                    .size(40.dp),
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.9f)
                    ) {
                        items(
                            listOf(
                                "spoon",
                                "cup",
                                "ball",
                                "block",
                                "clock",
                                "colorpencil",
                                "doll",
                                "scissor",
                                "socks",
                                "toothbrush"
                            )
                        ) { GameItemCategory ->
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    modifier = Modifier.padding(start = 10.dp),
                                    text = GameItemCategory,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                ) {
                                    for (i in 1..3) {
                                        val imageName = "${GameItemCategory}_${i}"
                                        val imageResource = getResourceIdByName(imageName, context)
                                        val isSelected = i - 1 == selectedIdxMap.getOrDefault(GameItemCategory, -1)
                                        Image(
                                            modifier = Modifier
                                                .weight(1.0f)
                                                .padding(10.dp)
                                                .clickable {

                                                    if (isSelected) {
                                                        selectedGameItems.remove(imageName)
                                                        selectedIdxMap[GameItemCategory] = -1
                                                    } else {
                                                        selectedGameItems.removeAll {
                                                            it.startsWith(
                                                                GameItemCategory
                                                            )
                                                        }
                                                        selectedGameItems.add(imageName)
                                                        selectedIdxMap[GameItemCategory] = i - 1
                                                    }
                                                },
                                            painter = painterResource(id = imageResource),
                                            contentDescription = null,
                                            alpha =  if (isSelected) 1.0f else 0.5f
                                        )
                                    }
                                }
                            }
                        }

                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(10.dp),
                        onClick = {
                            Log.e("선택된 게임들", selectedGameItems.toList().toString())
                            setAddGameItem(false)
                        },
                        shape = RoundedCornerShape(12.dp)
                    ){
                        Text(
                            text = "카드 준비",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
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
                setSelectedSTO(it)
                setSelectedSTOId(stoViewModelByDataClass.getSTOIdByCriteria(
                    childClass = childViewModel.selectedChildClass,
                    childName = childViewModel.selectedChildName,
                    selectedDEV = stoViewModelByDataClass.developZoneItems[selectedDEVIndex],
                    selectedLTO = selectedLTO,
                    it
                )!!)
                Log.e("선택 STO", selectedSTOIndex.toString())
                // 이 부분을 LaunchedEffect로 감싸서 업데이트를 다음 프레임으로 보내줍니다.
                //selectedSTODetailList가 바로 적용되지 않고 한턴씩 밀림 UI에 볂
                setSelectedSTODetail(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDEVIndex, newIndex).first.toMutableList())
                setSTODetailListIndex(progressState)
                setSelectedSTODetailGameDataIndex(0)
            },
            deleteSTOItem = {
                stoViewModelByDataClass.deleteSTO(stoViewModelByDataClass.getSTOIdByCriteria(childClass = childViewModel.selectedChildClass, childName = childViewModel.selectedChildName, stoViewModelByDataClass.developZoneItems[selectedDEVIndex],selectedLTO, it)!!)
                setSelectedSTO("")
                setSelectedSTODetailGameDataIndex(0)
            }
        )
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
        //STO Details Row---------------------------------------------------------------------------
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
            selectedLTO = selectedLTO,
            selectedSTODetailGameDataIndex = selectedSTODetailGameDataIndex,
            stoDetailViewModel = stoDetailViewModel,
            setSelectedSTODetailGameDataIndex = {setSelectedSTODetailGameDataIndex(it)},
            setSTODetailListIndex = {setSTODetailListIndex(it)},
            graphViewModel = graphViewModel,
            ltoViewModel = ltoViewModel,
            childViewModel = childViewModel,
            stoViewModel = stoViewModel
        )
        //게임준비
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                .border(4.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
        ) {
            if(selectedSTO != ""){
                Box(modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
                    .padding(5.dp)
                    .clickable {
                        setAddGameItem(true)
                    },
                    contentAlignment = Alignment.Center
                ){
                    Column(modifier = Modifier
                        .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Icon(
                            modifier = Modifier
                                .size(80.dp),
                            painter = painterResource(id = R.drawable.icon_add_square_light),
                            contentDescription = null
                        )
                    }

                }

                LazyRow(
                ) {
                    items(selectedGameItems.toList()) { selectedGameItem ->
                        val imageResource = getResourceIdByName(selectedGameItem, context)
                        val isSelected = selectedGameItem == mainGameItem

                        Image(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(top = 10.dp, end = 10.dp, bottom = 10.dp)
                                .clickable {
                                    // Update the selected item when clicked
                                    if (isSelected) {
                                        setMainGameItem("")
                                    } else {
                                        setMainGameItem(selectedGameItem)
                                    }
                                },
                            painter = painterResource(id = imageResource),
                            contentDescription = null,
                            alpha = if (isSelected) 1.0f else 0.5f
                        )
                    }
                }
            }


        }
        //그래프
        if(isLTOGraphSelected){
            GraphRow(
                graphViewModel = graphViewModel,
                stoViewModel = stoViewModel,
                childViewModel = childViewModel,
                stoViewModelByDataClass = stoViewModelByDataClass,
                selectedDEVIndex = selectedDEVIndex,
                selectedLTOIndex = selectedLTOIndex,
                selectedLTO =selectedLTO
            )
        }



    }
}





@Composable
fun getResourceIdByName(imageName: String, context: Context): Int {
    // 이 함수는 이미지 리소스 이름을 리소스 ID로 변환합니다.
    val packageName = context.packageName
    return context.resources.getIdentifier(imageName, "drawable", packageName)
}






