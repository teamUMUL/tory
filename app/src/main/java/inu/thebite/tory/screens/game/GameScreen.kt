package inu.thebite.tory.screens.game

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import inu.thebite.tory.R
import inu.thebite.tory.database.STO.STOEntity
import inu.thebite.tory.screens.datasceen.GameViewModel
import inu.thebite.tory.screens.datasceen.STOViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.forEach

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GameScreen(
    context : Context,
    dragAndDropViewModel: DragAndDropViewModel,
    gameViewModel: GameViewModel,
    stoViewModel: STOViewModel,
    selectedSTO: STOEntity?,
    timerStart : MutableState<Boolean>,
    timerRestart : MutableState<Boolean>,
    selectedSTODetailGameDataIndex : MutableIntState,
    setSelectedSTODetailGameDataIndex : (Int) -> Unit,
    resetGameButtonIndex : () -> Unit,
) {
    val (isCardSelectEnd, setIsCardSelectEnd) = rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(isCardSelectEnd){
        if(isCardSelectEnd){
            if (selectedSTODetailGameDataIndex.intValue < selectedSTO!!.gameResult.size) {
                val changeList = selectedSTO.gameResult.toMutableList()
                changeList[selectedSTODetailGameDataIndex.intValue] = gameViewModel.oneGameResult.value!!
                selectedSTO.gameResult = changeList
                stoViewModel.updateSTO(selectedSTO)
                gameViewModel.setGameResultList(changeList)
                setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex.intValue+1)
            }
            gameViewModel.clearOneGameResult()
        }
    }

    val targetItems by dragAndDropViewModel.targetItems.collectAsState()

    val screenWidth = LocalConfiguration.current.screenWidthDp
    DragableScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        dragAndDropViewModel = dragAndDropViewModel
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                targetItems!!.forEach { gameItem ->
                    DropItem<GameItem>(
                        modifier = Modifier
                            .weight(1f)
                            .size(240.dp)
                    ) { isInBound, dragInGameItem ->
                        if(dragInGameItem != null){
                            LaunchedEffect(key1 = dragInGameItem){
                                //드래그해서 들어온 아이템의 이름과 드래그한 곳의 이름이 같은 경우에 맞다는 판정
                                if(dragInGameItem.name == gameItem.name){
                                    dragAndDropViewModel.updateGameItem(
                                        GameItem(
                                            dragInGameItem.name,
                                            R.drawable.ellipse
                                        )
                                    )
                                    dragAndDropViewModel.isCorrect()
                                    when (gameViewModel.oneGameResult.value) {
                                        "P" -> {
                                            gameViewModel.setOneGameResult("P")
                                        }
                                        "-" -> {
                                            gameViewModel.setOneGameResult("-")
                                        }
                                        else -> {
                                            gameViewModel.setOneGameResult("+")
                                        }
                                    }
                                }
                                //여기는 틀린 경우에 들어갈 행동
                                else{
                                    when (gameViewModel.oneGameResult.value) {
                                        "P" -> {
                                            gameViewModel.setOneGameResult("P")
                                        }
                                        "-" -> {
                                            gameViewModel.setOneGameResult("-")
                                        }
                                        else -> {
                                            gameViewModel.setOneGameResult("-")
                                        }
                                    }
                                    Log.e("정답 틀림", gameViewModel.oneGameResult.value.toString())

                                }
                                timerRestart.value = true
                                timerStart.value = false
                                resetGameButtonIndex()
                                setIsCardSelectEnd(true)

                            }
                        }
                        Image(
                            painter = painterResource(id = gameItem.image),
                            contentDescription = null,
                            alpha = if(isInBound) 0.5f else 1f
                        )


                    }
                }
            }

            DragTarget(
                dataToDrop = dragAndDropViewModel.mainItem.value,
                viewModel = dragAndDropViewModel,
                timerStart = timerStart,
                timerRestart = timerRestart,
                setIsCardSelectEnd = {setIsCardSelectEnd(it)},
                resetGameButtonIndex = {resetGameButtonIndex()}
            ) {
                if(!dragAndDropViewModel.isCurrentlyDragging){
                    Image(painter = painterResource(id = dragAndDropViewModel.mainItem.value!!.image), contentDescription = null)
                }
            }

        }

    }
}


