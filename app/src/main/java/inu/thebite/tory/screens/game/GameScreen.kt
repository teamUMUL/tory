package inu.thebite.tory.screens.game

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.R
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.game.viewmodel.GameViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GameScreen(
    context : Context,
    dragAndDropViewModel: DragAndDropViewModel,
    gameViewModel: GameViewModel,
    stoViewModel: STOViewModel,
    selectedSTO: StoResponse,
    selectedLTO: LtoResponse,
    timerStart : MutableState<Boolean>,
    timerRestart : MutableState<Boolean>,
    selectedSTODetailGameDataIndex : MutableIntState,
    setSelectedSTODetailGameDataIndex : (Int) -> Unit,
    resetGameButtonIndex : () -> Unit,
    setIsCardSelectEnd : (Boolean) -> Unit,
    isCardSelectEnd : Boolean
) {
    val (successDialog, setSuccessDialog) = remember{
        mutableStateOf(false)
    }

    val (firstSuccessImage, setFirstSuccessImage) = remember{
        mutableIntStateOf(0)
    }

    val (secondSuccessImage, setSecondSuccessImage) = remember{
        mutableIntStateOf(0)
    }

    if(successDialog){
//        SuccessDialog(
//            context = context,
//            image1 = firstSuccessImage,
//            image2 = secondSuccessImage,
//            setSuccessDialog = {setSuccessDialog(it)},
//            dragAndDropViewModel = dragAndDropViewModel,
//            selectedLTO = selectedLTO,
//            gameViewModel = gameViewModel
//        )
    }
    LaunchedEffect(successDialog){
        if(selectedLTO.game == "같은 사진 매칭") {
            if(isCardSelectEnd && !successDialog && gameViewModel.oneGameResult.value.isNotNull()){
//                selectedSTO?.let { selectedSTO ->
//                    val changeList = selectedSTO.gameResult.toMutableList()
//                    changeList[selectedSTODetailGameDataIndex.intValue] = gameViewModel.oneGameResult.value!!
//                    selectedSTO.gameResult = changeList
//                    stoViewModel.updateSTO(selectedSTO)
//                    gameViewModel.setGameResultList(changeList)
//                    setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex.intValue+1)
//                    if(dragAndDropViewModel.isRandomGame){
//                        val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
//                        dragAndDropViewModel.setMainItem(
//                            randomMainItem.copy(
//                                image = getResourceIdByName(randomMainItem.name, context)
//                            )
//                        )
//                    }
//                    gameViewModel.clearOneGameResult()
//                }
            }
        }
        if(selectedLTO.game == "일반화 매칭"){
            if(isCardSelectEnd && !successDialog && gameViewModel.oneGameResult.value.isNotNull()){
//                //-,P 저장
//                val changeList = selectedSTO!!.gameResult.toMutableList()
//                changeList[selectedSTODetailGameDataIndex.intValue] = gameViewModel.oneGameResult.value!!
//                selectedSTO.gameResult = changeList
//                stoViewModel.updateSTO(selectedSTO)
//                gameViewModel.setGameResultList(changeList)
//                setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex.intValue+1)
//
//                if(dragAndDropViewModel.isRandomGame){
//                    val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
//                    dragAndDropViewModel.setMainItem(randomMainItem)
//                    dragAndDropViewModel.setTwoMainDifferentImageInCategory(context, randomMainItem.name)
//
//                }
//                //이미지 변경(target에 있는 동일한 카테고리)
//                gameViewModel.clearOneGameResult()
            }
        }

    }

    val mainItem by dragAndDropViewModel.mainItem.collectAsState()
    if(selectedLTO.game == "같은 사진 매칭") {
        LaunchedEffect(isCardSelectEnd){
            if(isCardSelectEnd){
//                if (selectedSTODetailGameDataIndex.intValue < selectedSTO!!.gameResult.size) {
//                    if(gameViewModel.oneGameResult.value == "+" || gameViewModel.oneGameResult.value == "P"){
//                        setFirstSuccessImage(dragAndDropViewModel.mainItem.value!!.image)
//                        setSecondSuccessImage(dragAndDropViewModel.mainItem.value!!.image)
//                        setSuccessDialog(true)
//                    }
//                    if (gameViewModel.oneGameResult.value != "+"){
//                        val changeList = selectedSTO.gameResult.toMutableList()
//                        changeList[selectedSTODetailGameDataIndex.intValue] = gameViewModel.oneGameResult.value!!
//                        selectedSTO.gameResult = changeList
//                        stoViewModel.updateSTO(selectedSTO)
//                        gameViewModel.setGameResultList(changeList)
//                        setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex.intValue+1)
//                        if(dragAndDropViewModel.isRandomGame){
//                            val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
//                            dragAndDropViewModel.setMainItem(
//                                randomMainItem.copy(
//                                    image = getResourceIdByName(randomMainItem.name, context)
//                                )
//                            )
//                        }
//                        gameViewModel.clearOneGameResult()
//                    }
//                }
            }
        }
    }
    val (beforeCircleImage , setBeforeCircleImage) = remember {
        mutableIntStateOf(0)
    }
    if(selectedLTO.game == "일반화 매칭"){
        LaunchedEffect(isCardSelectEnd){
            if(isCardSelectEnd){
//                if (selectedSTODetailGameDataIndex.intValue < selectedSTO!!.gameResult.size) {
//                    //축하 이팩트
//                    if(gameViewModel.oneGameResult.value == "+" || gameViewModel.oneGameResult.value == "P"){
//                        setFirstSuccessImage(dragAndDropViewModel.firstMainItem.value!!.image)
//                        setSecondSuccessImage(beforeCircleImage)
//                        setSuccessDialog(true)
//                    }
//                    if (gameViewModel.oneGameResult.value != "+"){
//                        //-,P 저장
//                        val changeList = selectedSTO.gameResult.toMutableList()
//                        changeList[selectedSTODetailGameDataIndex.intValue] = gameViewModel.oneGameResult.value!!
//                        selectedSTO.gameResult = changeList
//                        stoViewModel.updateSTO(selectedSTO)
//                        gameViewModel.setGameResultList(changeList)
//                        setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex.intValue+1)
//
//                        if(dragAndDropViewModel.isRandomGame){
//                            val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
//                            dragAndDropViewModel.setMainItem(randomMainItem)
//                            dragAndDropViewModel.setTwoMainDifferentImageInCategory(context, randomMainItem.name)
//
//                        }
//                        //이미지 변경(target에 있는 동일한 카테고리)
//                        gameViewModel.clearOneGameResult()
//                    }
//                }
            }
        }
    }

    val oneGameResult by gameViewModel.oneGameResult.collectAsState()
    val targetItems by dragAndDropViewModel.targetItems.collectAsState()


    val screenWidth = LocalConfiguration.current.screenWidthDp
    val cardSize = Dp(screenWidth / 6.5f)

    selectedLTO?.let { selectedLTO ->
        when (selectedLTO.game) {
            "같은 사진 매칭" -> {
                targetItems?.let {targetItems ->
                    SameGameScreen(
                        selectedLTO = selectedLTO,
                        timerStart = timerStart,
                        timerRestart = timerRestart,
                        targetItems = targetItems,
                        cardSize = cardSize,
                        oneGameResult = oneGameResult,
                        isCardSelectEnd = isCardSelectEnd,
                        dragAndDropViewModel = dragAndDropViewModel,
                        gameViewModel = gameViewModel,
                        resetGameButtonIndex = {resetGameButtonIndex()},
                        setIsCardSelectEnd = {setIsCardSelectEnd(it)}
                    )
                }
            }
            "일반화 매칭" -> {
                targetItems?.let {targetItems ->
//                    GeneralGameScreen(
//                        selectedLTO = selectedLTO,
//                        context = context,
//                        timerStart = timerStart,
//                        timerRestart = timerRestart,
//                        targetItems = targetItems,
//                        cardSize = cardSize,
//                        oneGameResult = oneGameResult,
//                        isCardSelectEnd = isCardSelectEnd,
//                        dragAndDropViewModel = dragAndDropViewModel,
//                        gameViewModel = gameViewModel,
//                        resetGameButtonIndex = {resetGameButtonIndex()},
//                        setIsCardSelectEnd = {setIsCardSelectEnd(it)},
//                        setBeforeCircleImage = {setBeforeCircleImage(it)}
//                    )
                }
            }
            else -> {

            }
        }
    }


}
