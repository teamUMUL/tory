@file:Suppress("NAME_SHADOWING")

package inu.thebite.tory.screens.DataScreen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import co.yml.charts.common.extensions.isNotNull


import inu.thebite.tory.ChildViewModel
import inu.thebite.tory.R
import inu.thebite.tory.database.STO.STOEntity
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt



@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun DataScreen (
    ltoViewModel: LTOViewModel = viewModel(),
    childViewModel: ChildViewModel = viewModel(),
    stoViewModel: STOViewModel = viewModel(),
    gameViewModel: GameViewModel = viewModel()
) {

    val context = LocalContext.current

    val scrollState = rememberScrollState()

    val selectedChildName by childViewModel.selectedChildName.observeAsState("오전1")
    val selectedChildClass by childViewModel.selectedChildClass.observeAsState("오전반(월수금)")
    
    val oneGameResult by gameViewModel.oneGameResult.observeAsState("+")
    val gameIndex by gameViewModel.gameIndex.observeAsState(-1)
    val gameResultList by gameViewModel.gameResultList.observeAsState()


    val stos by stoViewModel.stos.collectAsState()
    val selectedSTO by stoViewModel.selectedSTO.collectAsState()
    val allSTOs by stoViewModel.allSTOs.collectAsState()

    val ltos by ltoViewModel.ltos.collectAsState()
    val selectedLTO by ltoViewModel.selectedLTO.collectAsState()
    val allLTOs by ltoViewModel.allLTOs.collectAsState()


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
    val (selectedDEVIndex, setSelectedDEVIndex) = rememberSaveable {
        mutableStateOf(0)
    }
    val (gameButtonIndex, setGameButtonIndex) = rememberSaveable {
        mutableStateOf(-1)
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

    val (isCardSelectEnd, setIsCardSelectEnd) = rememberSaveable {
        mutableStateOf(false)
    }
    val (selectedSTODetailGameDataIndex, setSelectedSTODetailGameDataIndex) = rememberSaveable {
        mutableIntStateOf(0)
    }

    val (selectedSTOTryNum, setSelectedSTOTryNum) = rememberSaveable {
        mutableIntStateOf(0)
    }


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



    val (mainGameItem, setMainGameItem) = rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(isCardSelectEnd){
        if(isCardSelectEnd){
            if (selectedSTODetailGameDataIndex < selectedSTO!!.gameResult.size) {
                val changeList = selectedSTO!!.gameResult.toMutableList()
                changeList[selectedSTODetailGameDataIndex] = oneGameResult
                selectedSTO!!.gameResult = changeList
                stoViewModel.updateSTO(selectedSTO!!)
                gameViewModel.setGameResultList(changeList)
                setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex+1)
            }
            gameViewModel.setOneGameResult("+")
        }
    }

    LaunchedEffect(selectedLTO, selectedDEVIndex, selectedChildClass, selectedChildName, addSTOItem, allSTOs, selectedSTO){
        selectedLTO?.let { stoViewModel.getSTOsByCriteria(selectedChildClass,selectedChildName, stoViewModel.developZoneItems[selectedDEVIndex], it.ltoName) }
    }

    LaunchedEffect(selectedChildClass, selectedChildName, selectedDEVIndex, allLTOs){
        ltoViewModel.getLTOsByCriteria(selectedChildClass,selectedChildName, stoViewModel.developZoneItems[selectedDEVIndex])
    }

    //LTO 추가 Dialog
    if (addLTOItem) {
        AddLTOItemDialog(
            setAddLTOItem = { setAddLTOItem(false) },
            ltoViewModel = ltoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedChildClass = selectedChildClass,
            selectedChildName = selectedChildName,
            stoViewModel = stoViewModel,
        )
    }

    //LTO 추가 Dialog
    if (updateLTOItem) {
        UpdateLTOItemDialog(
            setUpdateLTOItem = {setUpdateLTOItem(false)},
            ltoViewModel = ltoViewModel,
            stoViewModel = stoViewModel,
            stos = stos,
            selectedLTO = selectedLTO,
        )
    }

    //STO 추가 Dialog
    if (addSTOItem) {
        AddSTOItemDialog(
            setAddSTOItem = { setAddSTOItem(false) },
            selectedDevIndex = selectedDEVIndex,
            childViewModel = childViewModel,
            stoViewModel = stoViewModel,
            selectedLTO = selectedLTO,
            selectedChildClass = selectedChildClass,
            selectedChildName = selectedChildName,
            updateSTOs = {}
        )
    }
    //STO 추가 Dialog
    if (updateSTOItem) {
        UpdateSTOItemDialog(
            stoViewModel = stoViewModel,
            setUpdateSTOItem = {setUpdateSTOItem(it)},
            selectedSTO = selectedSTO!!,
            setSelectedTryNum = {setSelectedSTOTryNum(it)}
        )
    }

    val gameButtons = listOf("P", "C")
    val cornerRadius = 4.dp
    val timerStart = remember { mutableStateOf(false) }
    val timerRestart = remember { mutableStateOf(false) }
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
                        .background(MaterialTheme.colorScheme.tertiary),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(60.dp)
                            .padding(10.dp)
                            .clickable {
                                setGameDialog(false)
                            },
                        painter = painterResource(id = R.drawable.icon_back),
                        contentDescription = null
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(5.dp)
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.secondary,
                                RoundedCornerShape(4.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(7f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            LazyRow(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                selectedSTO?.let {
                                    gameViewModel.setGameResultList(it.gameResult)
                                    items(gameResultList!!) { gameResult ->
                                        if (gameResult != "n") {
                                            Text(
                                                modifier = Modifier
                                                    .padding(start = 5.dp),
                                                fontSize = 40.sp,
                                                text = gameResult,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }


                                    }
                                }
                            }

                        }
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Timer(timerStart = timerStart, timerRestart = timerRestart, gameButtonIndex = gameButtonIndex, oneGameResult = oneGameResult, gameViewModel = gameViewModel)
                        }

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            gameButtons.forEachIndexed { index, item ->
                                OutlinedButton(
                                    onClick = {
                                        setGameButtonIndex(index)
                                        if(index == gameButtonIndex){
                                            setGameButtonIndex(-1)
                                        }
                                        if(item == "C"){
                                            gameViewModel.setOneGameResult("-")
                                        }else{
                                            gameViewModel.setOneGameResult("P")
                                        }
                                    },
                                    shape = when (index) {
                                        // left outer button
                                        0 -> RoundedCornerShape(
                                            topStart = cornerRadius,
                                            topEnd = 0.dp,
                                            bottomStart = cornerRadius,
                                            bottomEnd = 0.dp
                                        )
                                        // right outer button
                                        gameButtons.size - 1 -> RoundedCornerShape(
                                            topStart = 0.dp,
                                            topEnd = cornerRadius,
                                            bottomStart = 0.dp,
                                            bottomEnd = cornerRadius
                                        )
                                        // middle button
                                        else -> RoundedCornerShape(
                                            topStart = 0.dp,
                                            topEnd = 0.dp,
                                            bottomStart = 0.dp,
                                            bottomEnd = 0.dp
                                        )
                                    },
                                    border = BorderStroke(
                                        1.dp,
                                        if (gameButtonIndex == index) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary.copy(
                                            alpha = 0.75f
                                        )
                                    ),
                                    modifier = when (index) {
                                        0 ->
                                            Modifier
                                                .offset(0.dp, 0.dp)
                                                .zIndex(if (gameButtonIndex == index) 1f else 0f)

                                        else ->
                                            Modifier
                                                .offset((-1 * index).dp, 0.dp)
                                                .zIndex(if (gameButtonIndex == index) 1f else 0f)
                                    },
                                    colors =
                                    if (gameButtonIndex == index) {
                                        ButtonDefaults.outlinedButtonColors(
                                            containerColor = MaterialTheme.colorScheme.primary.copy(
                                                alpha = 0.5f
                                            ),
                                            contentColor = Color.Black
                                        )
                                    } else {
                                        ButtonDefaults.outlinedButtonColors(
                                            containerColor = MaterialTheme.colorScheme.tertiary,
                                            contentColor = Color.Black
                                        )
                                    }

                                ) {
                                    Text(
                                        text = gameButtons[index],
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    val isCollided = remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val imageModifier = Modifier
                            .size(320.dp)
                            .padding(16.dp)

                        Image(
                            painter = painterResource(id = R.drawable.socks_1),
                            contentDescription = null,
                            modifier = imageModifier,
                            contentScale = ContentScale.Fit
                        )
                        Image(
                            painter = if (isCollided.value) painterResource(id = R.drawable.ellipse) else painterResource(id = R.drawable.spoon_2),
                            contentDescription = null,
                            modifier = imageModifier,
                            contentScale = ContentScale.Fit
                        )

                        Image(
                            painter = painterResource(id = R.drawable.toothbrush_2),
                            contentDescription = null,
                            modifier = imageModifier,
                            contentScale = ContentScale.Fit
                        )
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                    selectedSTO?.let {
                        DraggableObject(
                            image = painterResource(id = R.drawable.spoon_1),
                            isDragging = isCollided,
                            timerStart = timerStart,
                            timerRestart = timerRestart,
                            resetGameButtonIndex = {setGameButtonIndex(-1)},
                            onCollision = {isCollided.value = true},
                            setIsCardSelectEnd = { it -> setIsCardSelectEnd(it)},
                        )
                    }
                }
            }

        }
    }

    if(addGameItem){
        val selectedGameItems by remember {
            mutableStateOf(selectedSTO!!.gameItems.toMutableList())
        }
        val selectedIdxMap = remember {
            mutableStateMapOf<String, Int>()
        }
        for (selectedGameItem in selectedSTO!!.gameItems) {
            val parts = selectedGameItem.split("_")
            if (parts.size == 2) {
                val category = parts[0]
                val index = parts[1].toIntOrNull()
                if (index != null) {
                    // 카테고리와 인덱스를 selectedIdxMap에 추가
                    selectedIdxMap[category] = index-1
                }
            }
        }
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
                                LazyRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                ) {
                                    items(3){ i ->
                                        val i = i + 1
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

                                                    selectedSTO!!.gameItems = selectedGameItems
                                                    stoViewModel.updateSTO(selectedSTO!!)
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
                            selectedSTO!!.gameItems = selectedGameItems
                            stoViewModel.updateSTO(selectedSTO!!)
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
                ltoViewModel.clearSelectedLTO()
                stoViewModel.clearSelectedSTO()
                Log.e("버그위치", "버그위치")
                setSelectedSTODetailGameDataIndex(0)
            },
        )
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
        //LTO ITEM----------------------------------------------------------------------------------
        LTOItemsRow(
            ltoViewModel = ltoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTO = selectedLTO,
            ltos = ltos,
            setAddLTOItem = {setAddLTOItem(it)},
            selectLTOItem = { it: String, progressState:Int ->
                setLTODetailListIndex(progressState)
                setSelectedSTODetailGameDataIndex(0)
            },
            stoViewModel = stoViewModel,
            selectedChildClass = selectedChildClass,
            selectedChildName = selectedChildName
        )
        //LTO Detail--------------------------------------------------------------------------------
        LTODetailsRow(
            selectedLTO = selectedLTO,
            isGraphSelected = isLTOGraphSelected,
            setGraphSelected = { setIsLTOGraphSelected(it) },
            setDetailListIndex = { setLTODetailListIndex(it) },
            ltoViewModel = ltoViewModel,
            selectedDevIndex = selectedDEVIndex,
            ltoDetailListIndex = ltoDetailListIndex,
            setLTOUpdateDialog = {setUpdateLTOItem(it)}
        )
        //STO ITEM ---------------------------------------------------------------------------------

        STOItemsRow(
            stoViewModel = stoViewModel,
            selectedLTO = selectedLTO,
            selectedSTO = selectedSTO,
            stos = stos,
            allSTOs = allSTOs,
            setAddSTOItem = { setAddSTOItem(it) },
            selectSTOItem = { it: String, progressState:Int ->
                setSTODetailListIndex(progressState)
                setSelectedSTODetailGameDataIndex(0)
            },
            setSelectedSTOTryNum = {setSelectedSTOTryNum(it)}
        )
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
        //STO Details Row---------------------------------------------------------------------------
        selectedSTO?.let {
            STODetailsRow(
                selectedSTO = it,
                stoDetailListIndex = stoDetailListIndex,
                setSTODetailIndex = { setSTODetailListIndex(it) },
                setUpdateSTOItem = {setUpdateSTOItem(it)},
                gameStart = {
                    setGameDialog(true)
                    val targetElement = "n"
                    val firstNIndex = it.gameResult.indexOf(targetElement)
                    //n이 없는 경우에는 0으로 n이 있는 경우에는 처음으로 n이 있는 인덱스로 설정
                    if(firstNIndex != -1){
                        setSelectedSTODetailGameDataIndex(firstNIndex)
                    }else {
                        setSelectedSTODetailGameDataIndex(0)
                    }
                },
                stoViewModel = stoViewModel
            )
        }
        //STO Detail 내용 및 게임결과
        selectedSTO?.let {
            STODetailTableAndGameResult(
                selectedSTO = it,
                selectedSTODetailGameDataIndex = selectedSTODetailGameDataIndex,
                setSelectedSTODetailGameDataIndex = {setSelectedSTODetailGameDataIndex(it)},
                setSTODetailListIndex = {setSTODetailListIndex(it)},
                stoViewModel = stoViewModel,
                selectedSTOTryNum = selectedSTOTryNum
            )
        }
        //게임준비
        if(selectedSTO.isNotNull()){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .border(4.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
            ) {
                if(selectedSTO.isNotNull()){
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
                        items(selectedSTO?.gameItems ?: emptyList()) { selectedGameItem ->
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
        }

        //그래프
        if(isLTOGraphSelected && selectedLTO.isNotNull()){
            GraphRow(
                stos = stos,
            )
        }



    }
}












@Composable
fun DraggableObject(
    image: Painter,
    isDragging: MutableState<Boolean>,
    timerStart: MutableState<Boolean>,
    timerRestart: MutableState<Boolean>,
    resetGameButtonIndex:() -> Unit,
    onCollision: () -> Unit,
    setIsCardSelectEnd: (Boolean) -> Unit
){
    //density를 넣을 경우 터치 민감도가 낮아지는 현상이 발견됨 -> density 사용 X
    val density = LocalDensity.current.density
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    x = offsetX.value.roundToInt(),
                    y = offsetY.value.roundToInt()
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        //드래그 시작 시 타이머 시작 및 카드 선택 상태 false로 설정
                        timerStart.value = true
                        setIsCardSelectEnd(false)
                    },
                    onDragEnd = {
                        //드래그 종료 시 원래 카드 위치로 옮기기 및 드래그 상태, 타이머 상태, 카트 선택 상태 설정
                        offsetX.value = 0f
                        offsetY.value = 0f
                        isDragging.value = false
                        timerRestart.value = true
                        timerStart.value = false
                        resetGameButtonIndex()
                        setIsCardSelectEnd(true)

                    },
                ) { _, dragAmount ->
                    offsetX.value += dragAmount.x
                    offsetY.value += dragAmount.y
                    isDragging.value = true
                }
            }
            .size(320.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .size(320.dp),
            contentScale = ContentScale.Fit
        )
    }

    if (isColliding(offsetX.value)) {
        onCollision()
    }
}

private fun isColliding(offsetX: Float): Boolean {
//     Implement collision detection logic here
//     You can customize this logic to check if the DraggableObject and the first image are colliding
//     For simplicity, we'll assume they collide when the DraggableObject is dragged to a certain position on the screen.
//     You can use the offsetX parameter to determine the position of DraggableObject.
    val collisionThreshold = 1000 // Adjust this threshold as needed
    return offsetX > collisionThreshold
}




@Composable
fun Timer(timerStart: MutableState<Boolean>, timerRestart: MutableState<Boolean>,oneGameResult: String, gameButtonIndex : Int, gameViewModel : GameViewModel) {
    var progress by remember { mutableStateOf(1f) }

    LaunchedEffect(timerStart.value, timerRestart.value) {
        if(gameButtonIndex == -1){
            if(timerStart.value){
                val startTime = System.currentTimeMillis()
                val duration = 5000L // 5 seconds

                while (progress > 0f) {
                    val elapsedTime = System.currentTimeMillis() - startTime
                    progress = 1 - (elapsedTime.toFloat() / duration)
                    delay(16) // Update every 16ms (approximately 60 FPS)
                }
                gameViewModel.setOneGameResult("-")
                timerRestart.value = true
                timerStart.value = false
            }
            if(timerRestart.value){
                progress = 1f
            }
        }


    }

    LinearProgressIndicator(
        modifier = Modifier.fillMaxSize(),
        progress = progress,
        color = MaterialTheme.colorScheme.primary // Customize the color as needed
    )
}































































































































@SuppressLint("DiscouragedApi")
@Composable
fun getResourceIdByName(imageName: String, context: Context): Int {
    // 이 함수는 이미지 리소스 이름을 리소스 ID로 변환합니다.
    val packageName = context.packageName
    return context.resources.getIdentifier(imageName, "drawable", packageName)
}







