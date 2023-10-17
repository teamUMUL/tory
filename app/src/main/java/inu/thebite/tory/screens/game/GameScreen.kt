//package inu.thebite.tory.screens.game
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.util.Log
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.Divider
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.MutableIntState
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import inu.thebite.tory.R
//import inu.thebite.tory.database.STO.STOEntity
//import inu.thebite.tory.screens.education.GameViewModel
//import inu.thebite.tory.screens.education.STOViewModel
//import inu.thebite.tory.screens.education.getRandomIndex
//import inu.thebite.tory.screens.education.getResourceIdByName
//
//@SuppressLint("StateFlowValueCalledInComposition")
//@Composable
//fun GameScreen(
//    context : Context,
//    dragAndDropViewModel: DragAndDropViewModel,
//    gameViewModel: GameViewModel,
//    stoViewModel: STOViewModel,
//    selectedSTO: STOEntity?,
//    timerStart : MutableState<Boolean>,
//    timerRestart : MutableState<Boolean>,
//    selectedSTODetailGameDataIndex : MutableIntState,
//    setSelectedSTODetailGameDataIndex : (Int) -> Unit,
//    resetGameButtonIndex : () -> Unit,
//    setIsCardSelectEnd : (Boolean) -> Unit,
//    isCardSelectEnd : Boolean
//) {
//
//    val mainItem by dragAndDropViewModel.mainItem.collectAsState()
//
//    LaunchedEffect(isCardSelectEnd){
//        if(isCardSelectEnd){
//            if (selectedSTODetailGameDataIndex.intValue < selectedSTO!!.gameResult.size) {
//                val changeList = selectedSTO.gameResult.toMutableList()
//                changeList[selectedSTODetailGameDataIndex.intValue] = gameViewModel.oneGameResult.value!!
//                selectedSTO.gameResult = changeList
//                stoViewModel.updateSTO(selectedSTO)
//                gameViewModel.setGameResultList(changeList)
//                setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex.intValue+1)
//                if(dragAndDropViewModel.isRandomGame){
//                    val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!)]
//                    dragAndDropViewModel.setMainItem(
//                        randomMainItem.copy(
//                            image = getResourceIdByName(randomMainItem.name, context)
//                        )
//                    )
//                }
//            }
//            gameViewModel.clearOneGameResult()
//        }
//    }
//    val oneGameResult by gameViewModel.oneGameResult.collectAsState()
//    val targetItems by dragAndDropViewModel.targetItems.collectAsState()
//
//    val screenWidth = LocalConfiguration.current.screenWidthDp
//    val cardSize = Dp(screenWidth / 6.5f)
//    val group1Size = (targetItems!!.size + 1) / 2
//    val group1 = targetItems!!.subList(0, group1Size)
//    val group2 = targetItems!!.subList(group1Size, targetItems!!.size)
//    DragableScreen(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White),
//        dragAndDropViewModel = dragAndDropViewModel,
//        timerRestart = timerRestart
//    ){
//        Column(
//            modifier = Modifier
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ){
//            if(targetItems!!.size <= 3){
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight(0.6f),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center,
//                ){
//                    targetItems!!.forEach { gameItem ->
//                        DropItem<GameItem>(
//                            modifier = Modifier
//                                .weight(1f)
//                                .size(cardSize)
//                        ) { isInBound, dragInGameItem ->
//                            if(dragInGameItem != null){
//                                LaunchedEffect(key1 = dragInGameItem){
//                                    //드래그해서 들어온 아이템의 이름과 드래그한 곳의 이름이 같은 경우에 맞다는 판정
//                                    if(dragInGameItem.name == gameItem.name){
//                                        dragAndDropViewModel.updateGameItem(
//                                            GameItem(
//                                                dragInGameItem.name,
//                                                R.drawable.ellipse
//                                            )
//                                        )
//                                        dragAndDropViewModel.isCorrect()
//                                        when (oneGameResult) {
//                                            "P" -> {
//                                                gameViewModel.setOneGameResult("P")
//                                            }
//                                            "C" -> {
//                                                gameViewModel.setOneGameResult("C")
//                                            }
//                                            else -> {
//                                                gameViewModel.setOneGameResult("+")
//                                            }
//                                        }
//                                    }
//                                    //여기는 틀린 경우에 들어갈 행동
//                                    else{
//
//                                        when (oneGameResult) {
//
//                                            "P" -> {
//                                                gameViewModel.setOneGameResult("P")
//                                            }
//                                            "C" -> {
//                                                gameViewModel.setOneGameResult("C")
//                                            }
//                                            else -> {
//                                                gameViewModel.setOneGameResult("-")
//                                            }
//                                        }
//
//                                    }
//                                    timerRestart.value = true
//                                    timerStart.value = false
//                                    resetGameButtonIndex()
//                                    if(gameViewModel.oneGameResult.value == "C"){
//                                        gameViewModel.setOneGameResult("+")
//                                    } else {
//                                        setIsCardSelectEnd(true)
//
//                                    }
//
//
//                                }
//                            }
//
//                            Image(
//                                modifier = Modifier
//                                    .size(cardSize),
//                                painter = painterResource(id = gameItem.image),
//                                contentDescription = null,
//                                alpha = if(isInBound) 0.5f else 1f
//                            )
//
//
//
//                        }
//                    }
//                }
//            } else{
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight(0.65f),
//                    verticalArrangement = Arrangement.Top,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .fillMaxHeight(0.5f),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center,
//                    ){
//                        group1.forEach { gameItem ->
//                            DropItem<GameItem>(
//                                modifier = Modifier
//                                    .weight(1f)
//                                    .size(cardSize)
//                            ) { isInBound, dragInGameItem ->
//                                if(dragInGameItem != null){
//                                    LaunchedEffect(key1 = dragInGameItem){
//                                        //드래그해서 들어온 아이템의 이름과 드래그한 곳의 이름이 같은 경우에 맞다는 판정
//                                        if(dragInGameItem.name == gameItem.name){
//                                            dragAndDropViewModel.updateGameItem(
//                                                GameItem(
//                                                    dragInGameItem.name,
//                                                    R.drawable.ellipse
//                                                )
//                                            )
//                                            dragAndDropViewModel.isCorrect()
//                                            when (oneGameResult) {
//                                                "P" -> {
//                                                    gameViewModel.setOneGameResult("P")
//                                                }
//                                                "C" -> {
//                                                    gameViewModel.setOneGameResult("C")
//                                                }
//                                                else -> {
//                                                    gameViewModel.setOneGameResult("+")
//                                                }
//                                            }
//                                        }
//                                        //여기는 틀린 경우에 들어갈 행동
//                                        else{
//
//                                            when (oneGameResult) {
//
//                                                "P" -> {
//                                                    gameViewModel.setOneGameResult("P")
//                                                }
//                                                "C" -> {
//                                                    gameViewModel.setOneGameResult("C")
//                                                }
//                                                else -> {
//                                                    gameViewModel.setOneGameResult("-")
//                                                }
//                                            }
//
//                                        }
//                                        timerRestart.value = true
//                                        timerStart.value = false
//                                        resetGameButtonIndex()
//                                        if(gameViewModel.oneGameResult.value == "C"){
//                                            gameViewModel.setOneGameResult("+")
//                                        } else {
//                                            setIsCardSelectEnd(true)
//
//                                        }
//
//                                    }
//                                }
//
//                                Image(
//                                    modifier = Modifier
//                                        .size(cardSize),
//                                    painter = painterResource(id = gameItem.image),
//                                    contentDescription = null,
//                                    alpha = if(isInBound) 0.5f else 1f
//                                )
//                            }
//                        }
//                    }
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .fillMaxHeight(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center,
//                    ){
//                        group2.forEach { gameItem ->
//                            DropItem<GameItem>(
//                                modifier = Modifier
//                                    .weight(1f)
//                                    .size(cardSize)
//                            ) { isInBound, dragInGameItem ->
//                                if(dragInGameItem != null){
//                                    Log.e("dragInGameItem", dragInGameItem.name)
//                                    LaunchedEffect(key1 = dragInGameItem){
//                                        //드래그해서 들어온 아이템의 이름과 드래그한 곳의 이름이 같은 경우에 맞다는 판정
//                                        if(dragInGameItem.name == gameItem.name){
//                                            dragAndDropViewModel.updateGameItem(
//                                                GameItem(
//                                                    dragInGameItem.name,
//                                                    R.drawable.ellipse
//                                                )
//                                            )
//                                            dragAndDropViewModel.isCorrect()
//                                            when (oneGameResult) {
//                                                "P" -> {
//                                                    gameViewModel.setOneGameResult("P")
//                                                }
//                                                "C" -> {
//                                                    gameViewModel.setOneGameResult("C")
//                                                }
//                                                else -> {
//                                                    gameViewModel.setOneGameResult("+")
//                                                }
//                                            }
//                                        }
//                                        //여기는 틀린 경우에 들어갈 행동
//                                        else{
//
//                                            when (oneGameResult) {
//
//                                                "P" -> {
//                                                    gameViewModel.setOneGameResult("P")
//                                                }
//                                                "C" -> {
//                                                    gameViewModel.setOneGameResult("C")
//                                                }
//                                                else -> {
//                                                    gameViewModel.setOneGameResult("-")
//                                                }
//                                            }
//
//                                        }
//                                        timerRestart.value = true
//                                        timerStart.value = false
//                                        resetGameButtonIndex()
//                                        if(gameViewModel.oneGameResult.value == "C"){
//                                            gameViewModel.setOneGameResult("+")
//                                        } else {
//                                            setIsCardSelectEnd(true)
//
//                                        }
//
//
//
//                                    }
//                                }
//
//                                Image(
//                                    modifier = Modifier
//                                        .size(cardSize),
//                                    painter = painterResource(id = gameItem.image),
//                                    contentDescription = null,
//                                    alpha = if(isInBound) 0.5f else 1f
//                                )
//
//
//
//                            }
//                        }
//                    }
//                }
//
//            }
//
//
//            Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(500.dp)
//                    .background(MaterialTheme.colorScheme.secondary),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.Bottom
//            ) {
//                DragTarget(
//                    modifier = Modifier
//                        .size(cardSize)
//                        .padding(bottom = 20.dp)
//                    ,
//                    dataToDrop = dragAndDropViewModel.mainItem.value,
//                    viewModel = dragAndDropViewModel,
//                    timerStart = timerStart,
//                    timerRestart = timerRestart,
//                    isCardSelectEnd = isCardSelectEnd,
//                    setIsCardSelectEnd = {setIsCardSelectEnd(it)},
//                    resetGameButtonIndex = {resetGameButtonIndex()}
//                ) {
//                    if(!dragAndDropViewModel.isCurrentlyDragging){
//                        Image(
//                            modifier = Modifier
//                                .size(cardSize),
//                            painter = painterResource(id = dragAndDropViewModel.mainItem.value!!.image),
//                            contentDescription = null)
//                    }
//                }
//            }
//
//
//        }
//
//    }
//}
